package com.kibo.pevantivgateway.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class GatewayInteraction implements Serializable {
    @Getter
    @Setter
    Integer id;
    @Getter
    @Setter
    Calendar createdOn;
    @Getter
    @Setter
    TransactionType transactionType;
    @Getter
    @Setter
    KeyValueTuple[] responseData;
    @Getter
    @Setter
    Boolean isSuccessful;
    @Getter
    @Setter
    String cardId;
    @Getter
    @Setter
    BigDecimal amount;
    @Getter
    @Setter
    Boolean isDeleted;
}
