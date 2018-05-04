package com.kibo.pegateway.dto.override.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kibo.pegateway.dto.override.base.IGatewayAuthorizeResponse;
import com.kibo.pegateway.dto.override.jackson.common.KeyValueTupleDeserializer;
import com.kibo.pegateway.dto.override.jackson.common.KeyValueTupleSerializer;
import com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayCaptureResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

public class GatewayCaptureResponseOverride extends GatewayCaptureResponse implements IGatewayAuthorizeResponse, Serializable {
    @Getter
    @Setter
    @JsonSerialize(using = KeyValueTupleSerializer.class)
    @JsonDeserialize(using = KeyValueTupleDeserializer.class)
    @JsonProperty("responseData")
    Map<String, Object> responseDataMap;
}
