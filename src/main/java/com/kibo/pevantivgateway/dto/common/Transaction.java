package com.kibo.pevantivgateway.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class Transaction implements Serializable {
    @Getter
    @Setter
    String id;
    @Getter
    @Setter
    String kiboTransactionId;
    @Getter
    @Setter
    Boolean isRecurring;
    @Getter
    @Setter
    String activeCardId;
    @Getter
    @Setter
    GatewayInteraction[] gatewayInteractions;
    @Getter
    @Setter
    Calendar createdOn;
    @Getter
    @Setter
    String currencyCode;
}
