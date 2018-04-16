package com.kibo.pevantivgateway.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class AuthorizeRequest extends PaymentRequest {
    @Getter
    @Setter
    BigDecimal amount;
    @Getter
    @Setter
    Boolean preAuth;
    @Getter
    @Setter
    String recurringType;
}
