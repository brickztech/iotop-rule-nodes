/**
 * Copyright © 2018 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.rule.engine.node.transform;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;
import org.thingsboard.rule.engine.api.*;
import org.thingsboard.rule.engine.api.util.TbNodeUtils;
import org.thingsboard.rule.engine.node.util.EntitiesRelatedEntitiesAsyncLoader;
import org.thingsboard.rule.engine.transform.TbChangeOriginatorNodeConfiguration;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.plugin.ComponentType;
import org.thingsboard.server.common.msg.TbMsg;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.thingsboard.common.util.DonAsynchron.withCallback;
import static org.thingsboard.rule.engine.api.TbRelationTypes.SUCCESS;

@Slf4j
@RuleNode(
        type = ComponentType.TRANSFORMATION,
        name = "duplicate to related",
        configClazz = TbChangeOriginatorNodeConfiguration.class,
        nodeDescription = "Duplicates message to related entities fetched by relation query.",
        nodeDetails = "Related Entities found using configured relation direction and Relation Type. " +
                "For each found related entity new message is created with related entity as originator and message parameters copied from original message.",
        uiResources = {"static/rulenode/rulenode-core-config.js", "static/rulenode/rulenode-core-config.css"},
        configDirective = "tbTransformationNodeChangeOriginatorConfig",
        icon = "find_replace"
)
public class TbDuplicateToRelatedNode implements TbNode {

    protected static final String CUSTOMER_SOURCE = "CUSTOMER";
    protected static final String TENANT_SOURCE = "TENANT";
    protected static final String RELATED_SOURCE = "RELATED";
    protected static final String ALARM_ORIGINATOR_SOURCE = "ALARM_ORIGINATOR";

    private TbChangeOriginatorNodeConfiguration config;

    @Override
    public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
        this.config = TbNodeUtils.convert(configuration, TbChangeOriginatorNodeConfiguration.class);
        validateConfig(config);
    }

    @Override
    public void onMsg(TbContext ctx, TbMsg msg) {
        try {
            withCallback(
                    getNewOriginators(ctx, msg.getOriginator()),
                    entityIds -> transform(msg, ctx, entityIds),
                    t -> ctx.tellFailure(msg, t), ctx.getDbCallbackExecutor());
        } catch (Throwable th) {
            ctx.tellFailure(msg, th);
        }
    }

    private void transform(TbMsg msg, TbContext ctx, List<EntityId> entityIds) {
        entityIds.forEach(i -> {
            TbMsg newMsg = ctx.transformMsg(msg, msg.getType(), i, msg.getMetaData(), msg.getData());
            ctx.enqueueForTellNext(newMsg, SUCCESS);
        });
        ctx.ack(msg);
    }

    @Override
    public void destroy() {

    }

    private ListenableFuture<List<EntityId>> getNewOriginators(TbContext ctx, EntityId original) {
        return EntitiesRelatedEntitiesAsyncLoader.findEntityIdsAsync(ctx, original, config.getRelationsQuery());
//        switch (config.getOriginatorSource()) {
//            case CUSTOMER_SOURCE:
//                return EntitiesCustomerIdAsyncLoader.findEntityIdAsync(ctx, original);
//            case TENANT_SOURCE:
//                return EntitiesTenantIdAsyncLoader.findEntityIdAsync(ctx, original);
//            case RELATED_SOURCE:
//                return EntitiesRelatedEntitiesAsyncLoader.findEntitiesAsync(ctx, original, config.getRelationsQuery());
//            case ALARM_ORIGINATOR_SOURCE:
//                return EntitiesAlarmOriginatorIdAsyncLoader.findEntityIdAsync(ctx, original);
//            default:
//                return Futures.immediateFailedFuture(new IllegalStateException("Unexpected originator source " + config.getOriginatorSource()));
//        }
    }

    private void validateConfig(TbChangeOriginatorNodeConfiguration conf) {
        HashSet<String> knownSources = Sets.newHashSet(CUSTOMER_SOURCE, TENANT_SOURCE, RELATED_SOURCE, ALARM_ORIGINATOR_SOURCE);
        if (!knownSources.contains(conf.getOriginatorSource())) {
            log.error("Unsupported source [{}] for TbDuplicateToRelatedNode", conf.getOriginatorSource());
            throw new IllegalArgumentException("Unsupported source TbDuplicateToRelatedNode" + conf.getOriginatorSource());
        }

        if (conf.getOriginatorSource().equals(RELATED_SOURCE)) {
            if (conf.getRelationsQuery() == null) {
                log.error("Related source for TbDuplicateToRelatedNode should have relations query. Actual [{}]",
                        conf.getRelationsQuery());
                throw new IllegalArgumentException("Wrong config for Related Source in TbDuplicateToRelatedNode" + conf.getOriginatorSource());
            }
        }

    }
}
