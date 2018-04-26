package com.kibo.pevantivgateway.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

public class CustomerInformation implements Serializable {
    @Getter
    @Setter
    Address address;
    @Getter
    @Setter
    Contact contact;
    @Getter
    @Setter
    String requestorIp;
    @Getter
    @Setter
    String requestorUrl;
    @Getter
    @Setter
    String requestorUserAgent;
    @Getter
    @Setter
    String currencyCode;
    @Getter
    @Setter
    String customerId;
    @Getter
    @Setter
    String phoneNumber;
    @Getter
    @Setter
    Map<String, String> extendedInfo;
}
