package com.kibo.pevantivgateway.dto.response;

import lombok.Getter;
import lombok.Setter;

public class CreditGatewayResponse extends GatewayResponse {
    @Getter
    @Setter
    String transactionId;
}
