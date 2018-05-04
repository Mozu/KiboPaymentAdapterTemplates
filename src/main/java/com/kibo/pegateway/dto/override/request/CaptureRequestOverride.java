package com.kibo.pegateway.dto.override.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kibo.pegateway.dto.override.base.IGatewayRequest;
import com.kibo.pegateway.dto.override.jackson.common.KeyValueTupleDeserializer;
import com.kibo.pegateway.dto.override.jackson.common.KeyValueTupleSerializer;
import com.mozu.api.contracts.paymentservice.extensibility.v1.CaptureRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

public class CaptureRequestOverride extends CaptureRequest implements IGatewayRequest, Serializable {
    @Getter
    @Setter
    @JsonSerialize(using = KeyValueTupleSerializer.class)
    @JsonDeserialize(using = KeyValueTupleDeserializer.class)
    @JsonProperty("additionalData")
    Map<String, Object> additionalDataMap;
}
