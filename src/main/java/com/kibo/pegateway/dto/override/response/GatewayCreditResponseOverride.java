package com.kibo.pegateway.dto.override.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kibo.pegateway.dto.override.base.IGatewayAuthorizeResponse;
import com.kibo.pegateway.dto.override.base.IGatewayTransactionResponse;
import com.kibo.pegateway.dto.override.jackson.common.KeyValueTupleDeserializer;
import com.kibo.pegateway.dto.override.jackson.common.KeyValueTupleSerializer;
import com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayAuthorizeResponse;
import com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayCreditResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

public class GatewayCreditResponseOverride extends GatewayCreditResponse implements IGatewayTransactionResponse, Serializable {
    @Getter
    @Setter
    @JsonSerialize(using = KeyValueTupleSerializer.class)
    @JsonDeserialize(using = KeyValueTupleDeserializer.class)
    @JsonProperty("responseData")
    Map<String, Object> responseDataMap;
}
