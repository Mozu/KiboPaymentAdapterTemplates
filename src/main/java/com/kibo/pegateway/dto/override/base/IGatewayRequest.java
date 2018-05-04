package com.kibo.pegateway.dto.override.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mozu.api.contracts.paymentservice.extensibility.v1.AdapterContext;
import com.mozu.api.contracts.paymentservice.extensibility.v1.CardInformation;
import com.mozu.api.contracts.paymentservice.extensibility.v1.CustomerInformation;
import com.mozu.api.contracts.paymentservice.extensibility.v1.KeyValueTuple;

import java.util.Map;

public interface IGatewayRequest {
    public void setAdditionalDataMap(Map<String, Object> additionalDataMap);

    public Map<String, Object> getAdditionalDataMap();

    public void setAmount(Double amount);

    public Double getAmount();

    public void setApiVersion(String apiVersion);

    public String getApiVersion();

    public void setMethodName(String methodName);

    public String getMethodName();

    public void setCard(CardInformation cardInformation);

    public CardInformation getCard();

    public void setContext(AdapterContext adapterContext);

    public AdapterContext getContext();

    public void setShopper(CustomerInformation shopper);

    public CustomerInformation getShopper();
}
