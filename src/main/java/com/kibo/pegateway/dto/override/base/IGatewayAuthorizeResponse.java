package com.kibo.pegateway.dto.override.base;

public interface IGatewayAuthorizeResponse extends IGatewayTransactionResponse {
    public void setAuthCode(String authCode);

    public String getAuthCode();

    public void setAvsCodes(String avsCodes);

    public String getAvsCodes();

    public void setCvV2Codes(String cvV2Codes);

    public String getCvV2Codes();
}
