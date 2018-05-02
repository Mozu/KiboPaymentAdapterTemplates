package com.kibo.pegateway.controller;

import com.mozu.api.contracts.paymentservice.extensibility.v1.ConnectionStatuses;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum EResponseCleaners {
    GatewayAuthorizeResponse((o) -> {
        com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayAuthorizeResponse response = (com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayAuthorizeResponse) o;
        if (StringUtils.isEmpty(response.getResponseCode())) {
            response.setResponseCode("");
        }
        if (StringUtils.isEmpty(response.getResponseText())) {
            response.setResponseText("");
        }
        if (StringUtils.isEmpty(response.getTransactionId())) {
            response.setTransactionId("");
        }
        if (StringUtils.isEmpty(response.getAuthCode())) {
            response.setAuthCode("");
        }
        if (StringUtils.isEmpty(response.getAvsCodes())) {
            response.setAvsCodes("");
        }
        if (StringUtils.isEmpty(response.getCvV2Codes())) {
            response.setCvV2Codes("");
        }
        if(response.getIsDeclined() && response.getRemoteConnectionStatus() == ConnectionStatuses.Success)
            response.setRemoteConnectionStatus(ConnectionStatuses.Reject);
    }, (o, e) -> {
        com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayAuthorizeResponse response = (com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayAuthorizeResponse) o;
        response.setIsDeclined(true);
        response.setRemoteConnectionStatus(ConnectionStatuses.Error);
        response.setResponseText(e.toString());
    }),
    GatewayCaptureResponse((o) -> {
        com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayCaptureResponse response = (com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayCaptureResponse) o;
        if (StringUtils.isEmpty(response.getResponseCode())) {
            response.setResponseCode("");
        }
        if (StringUtils.isEmpty(response.getResponseText())) {
            response.setResponseText("");
        }
        if (StringUtils.isEmpty(response.getTransactionId())) {
            response.setTransactionId("");
        }
        if (StringUtils.isEmpty(response.getAuthCode())) {
            response.setAuthCode("");
        }
        if (StringUtils.isEmpty(response.getAvsCodes())) {
            response.setAvsCodes("");
        }
        if (StringUtils.isEmpty(response.getCvV2Codes())) {
            response.setCvV2Codes("");
        }
        if(response.getIsDeclined() && response.getRemoteConnectionStatus() == ConnectionStatuses.Success)
            response.setRemoteConnectionStatus(ConnectionStatuses.Reject);
    }, (o, e) -> {
        com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayCaptureResponse response = (com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayCaptureResponse) o;
        response.setIsDeclined(true);
        response.setRemoteConnectionStatus(ConnectionStatuses.Error);
        response.setResponseText(e.toString());
    }),
    GatewayCreditResponse((o) -> {
        com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayCreditResponse response = (com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayCreditResponse) o;
        if (StringUtils.isEmpty(response.getResponseCode())) {
            response.setResponseCode("");
        }
        if (StringUtils.isEmpty(response.getResponseText())) {
            response.setResponseText("");
        }
        if (StringUtils.isEmpty(response.getTransactionId())) {
            response.setTransactionId("");
        }
        if(response.getIsDeclined() && response.getRemoteConnectionStatus() == ConnectionStatuses.Success)
            response.setRemoteConnectionStatus(ConnectionStatuses.Reject);
    }, (o, e) -> {
        com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayCreditResponse response = (com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayCreditResponse) o;
        response.setIsDeclined(true);
        response.setRemoteConnectionStatus(ConnectionStatuses.Error);
        response.setResponseText(e.toString());
    }),
    GatewayVoidResponse((o) -> {
        com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayVoidResponse response = (com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayVoidResponse) o;
        if (StringUtils.isEmpty(response.getResponseCode())) {
            response.setResponseCode("");
        }
        if (StringUtils.isEmpty(response.getResponseText())) {
            response.setResponseText("");
        }
        if (StringUtils.isEmpty(response.getTransactionId())) {
            response.setTransactionId("");
        }
        if(response.getIsDeclined() && response.getRemoteConnectionStatus() == ConnectionStatuses.Success)
            response.setRemoteConnectionStatus(ConnectionStatuses.Reject);
    }, (o, e) -> {
        com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayVoidResponse response = (com.mozu.api.contracts.paymentservice.extensibility.v1.GatewayVoidResponse) o;
        response.setIsDeclined(true);
        response.setRemoteConnectionStatus(ConnectionStatuses.Error);
        response.setResponseText(e.toString());
    });

    IResponseCleaner handleResponse;
    IErrorResponse errorResponse;

    private static Map<String, EResponseCleaners> responseCleanersMap;
    private static Object lock = new Object();

    private EResponseCleaners(IResponseCleaner handleResponse, IErrorResponse errorResponse) {
        this.handleResponse = handleResponse;
        this.errorResponse = errorResponse;
    }

    static void init() {
        if (responseCleanersMap == null) {
            synchronized (lock) {
                if (responseCleanersMap == null) {
                    responseCleanersMap = new HashMap<>();
                    for (EResponseCleaners responseCleaner : EResponseCleaners.values())
                        responseCleanersMap.put(responseCleaner.name(), responseCleaner);
                }
            }
        }
    }

    public static void cleanResponse(Object response) throws Exception {
        init();
        EResponseCleaners responseCleaner = responseCleanersMap.get(response.getClass().getSimpleName());
        if (responseCleaner == null) {
            return;
        }
        responseCleaner.handleResponse.clean(response);
    }

    public static void errorResponse(Object response, Exception e) throws Exception {
        init();
        EResponseCleaners responseCleaner = responseCleanersMap.get(response.getClass().getSimpleName());
        if(responseCleaner == null)
            return;
        responseCleaner.errorResponse.error(response, e);
        responseCleaner.handleResponse.clean(response);
    }
}
