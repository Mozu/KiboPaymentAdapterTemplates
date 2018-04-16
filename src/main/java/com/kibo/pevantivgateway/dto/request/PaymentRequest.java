package com.kibo.pevantivgateway.dto.request;

import com.kibo.pevantivgateway.dto.common.AdapterContext;
import com.kibo.pevantivgateway.dto.common.CardInformation;
import com.kibo.pevantivgateway.dto.common.CustomerInformation;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

public class PaymentRequest implements Serializable {
    @Getter
    @Setter
    String apiVersion;
    @Getter
    @Setter
    String methodName;
    @Getter
    @Setter
    CustomerInformation shopper;
    @Getter
    @Setter
    CardInformation card;
    @Getter
    @Setter
    Map<String, String> additionalData;
    @Getter
    @Setter
    AdapterContext context;
}
