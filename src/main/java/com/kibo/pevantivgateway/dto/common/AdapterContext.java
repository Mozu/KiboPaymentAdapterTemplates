package com.kibo.pevantivgateway.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

public class AdapterContext implements Serializable {
    @Getter
    @Setter
    Map<String, String> settings;
    @Getter
    @Setter
    Boolean isSandbox;
    @Getter
    @Setter
    Boolean isTestMode;
    @Getter
    @Setter
    Map<String, String> configuration;
    @Getter
    @Setter
    Map<String, String> transaction;
}
