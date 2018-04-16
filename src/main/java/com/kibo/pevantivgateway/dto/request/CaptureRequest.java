package com.kibo.pevantivgateway.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class CaptureRequest extends PaymentRequest {
    @Getter
    @Setter
    BigDecimal amount;
}
