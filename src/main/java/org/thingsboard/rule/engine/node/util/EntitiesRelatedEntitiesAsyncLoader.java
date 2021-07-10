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
package org.thingsboard.rule.engine.node.util;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.thingsboard.rule.engine.api.TbContext;
import org.thingsboard.rule.engine.data.RelationsQuery;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.relation.EntityRelation;
import org.thingsboard.server.common.data.relation.EntityRelationsQuery;
import org.thingsboard.server.common.data.relation.EntitySearchDirection;
import org.thingsboard.server.common.data.relation.RelationsSearchParameters;
import org.thingsboard.server.dao.relation.RelationService;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class EntitiesRelatedEntitiesAsyncLoader {

    public static ListenableFuture<List<EntityId>> findEntityIdsAsync(TbContext ctx, EntityId originator,
                                                                     RelationsQuery relationsQuery) {
        RelationService relationService = ctx.getRelationService();
        EntityRelationsQuery query = buildQuery(originator, relationsQuery);
        ListenableFuture<List<EntityRelation>> asyncRelation = relationService.findByQuery(ctx.getTenantId(), query);
            if (relationsQuery.getDirection() == EntitySearchDirection.FROM) {
                // List<EntityId> entities = asyncRelation.get().stream().map(EntityRelation::getTo).collect(Collectors.toList());
                // return entities;
                AsyncFunction<List<EntityRelation>, List<EntityId>> transformFunction = new AsyncFunction<List<EntityRelation>, List<EntityId>>() {
                    @Override
                    public ListenableFuture<List<EntityId>> apply(@Nullable List<EntityRelation> input) throws Exception {
                        return Futures.immediateFuture(input.stream().map(EntityRelation::getTo).collect(Collectors.toList()));
                    }
                };
                return Futures.transformAsync(asyncRelation, transformFunction, MoreExecutors.directExecutor());
/*
             Futures.transformAsync(asyncRelation, r -> CollectionUtils.isNotEmpty(r) ? Futures.immediateFuture(r.get(0).getTo())
                    : Futures.immediateFuture(null), MoreExecutors.directExecutor());
*/
            } else if (relationsQuery.getDirection() == EntitySearchDirection.TO) {
/*
                List<EntityId> entities = asyncRelation.get().stream().map(EntityRelation::getFrom).collect(Collectors.toList());
                return entities;
*/
            /* return Futures.transformAsync(asyncRelation, r -> CollectionUtils.isNotEmpty(r) ? Futures.immediateFuture(r.get(0).getFrom())
                    : Futures.immediateFuture(null), MoreExecutors.directExecutor()); */
                return Futures.transformAsync(asyncRelation,
                        r -> Futures.immediateFuture(r.stream().map(EntityRelation::getFrom).collect(Collectors.toList())),
                        MoreExecutors.directExecutor());
            }
            return Futures.immediateFailedFuture(new InterruptedException("Unknown direction"));
    }

    private static EntityRelationsQuery buildQuery(EntityId originator, RelationsQuery relationsQuery) {
        EntityRelationsQuery query = new EntityRelationsQuery();
        RelationsSearchParameters parameters = new RelationsSearchParameters(originator,
                relationsQuery.getDirection(), relationsQuery.getMaxLevel(), relationsQuery.isFetchLastLevelOnly());
        query.setParameters(parameters);
        query.setFilters(relationsQuery.getFilters());
        return query;
    }

}
