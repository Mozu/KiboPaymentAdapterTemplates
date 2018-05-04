package com.kibo.pegateway.dto.override.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kibo.pegateway.dto.override.jackson.common.KeyValueTupleDeserializer;
import com.kibo.pegateway.dto.override.jackson.common.KeyValueTupleSerializer;
import com.mozu.api.contracts.paymentservice.extensibility.v1.AdapterContext;
import com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayInteraction;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class GatewayInteractionOverride extends GatewayInteraction {
    @Getter
    @Setter
    @JsonSerialize(using = KeyValueTupleSerializer.class)
    @JsonDeserialize(using = KeyValueTupleDeserializer.class)
    @JsonProperty("configuration")
    Map<String, Object> configurationMap;
}
