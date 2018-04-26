package com.kibo.pevantivgateway.dto.response;

import lombok.Getter;
import lombok.Setter;

public class CaptureGatewayResponse extends GatewayResponse {
    @Getter
    @Setter
    String authCode;
    @Getter
    @Setter
    String transactionId;
    @Getter
    @Setter
    String avsCodes;
    @Getter
    @Setter
    String cvv2Codes;
}
