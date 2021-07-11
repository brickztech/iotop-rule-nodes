package org.thingsboard.rule.engine.node.enrichment;

import lombok.Data;
import org.thingsboard.rule.engine.api.NodeConfiguration;
import org.thingsboard.rule.engine.data.DeviceRelationsQuery;
import org.thingsboard.server.common.data.kv.Aggregation;
import org.thingsboard.server.common.data.relation.EntityRelation;
import org.thingsboard.server.common.data.relation.EntitySearchDirection;

import java.util.Collections;

@Data
public class TbGetTelemetryForEntityNodeConfiguration implements NodeConfiguration<TbGetTelemetryForEntityNodeConfiguration> {

    private DeviceRelationsQuery deviceRelationsQuery;
    private String aggregateFunction;
    private String inputKey;
    private String outputKey;

    @Override
    public TbGetTelemetryForEntityNodeConfiguration defaultConfiguration() {
        DeviceRelationsQuery deviceRelationsQuery = new DeviceRelationsQuery();
        deviceRelationsQuery.setDirection(EntitySearchDirection.FROM);
        deviceRelationsQuery.setMaxLevel(1);
        deviceRelationsQuery.setRelationType(EntityRelation.CONTAINS_TYPE);
        deviceRelationsQuery.setDeviceTypes(Collections.singletonList("default"));

        TbGetTelemetryForEntityNodeConfiguration configuration = new TbGetTelemetryForEntityNodeConfiguration();
        configuration.setDeviceRelationsQuery(deviceRelationsQuery);
        configuration.setAggregateFunction(Aggregation.SUM.toString());
        configuration.setInputKey("power");
        configuration.setOutputKey("PowerSum");
        return configuration;
    }

}
