package com.kibo.pegateway.dto.override.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mozu.api.contracts.paymentservice.extensibility.v1.ConnectionStatuses;
import com.mozu.api.contracts.paymentservice.extensibility.v1.KeyValueTuple;

import java.util.List;
import java.util.Map;

public interface IGatewayTransactionResponse {
    public void setIsDeclined(Boolean isDeclined);

    public Boolean getIsDeclined();

    public void setResponseCode(String responseCode);

    public String getResponseCode();

    public void setResponseText(String responseText);

    public String getResponseText();

    public void setTransactionId(String transactionId);

    public String getTransactionId();

    public void setRemoteConnectionStatus(ConnectionStatuses remoteConnectionstatus);

    public ConnectionStatuses getRemoteConnectionStatus();

    public void setResponseDataMap(Map<String, Object> responseDataMap);

    public Map<String, Object> getResponseDataMap();
}
