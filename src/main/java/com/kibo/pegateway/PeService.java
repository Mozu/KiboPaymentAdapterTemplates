package com.kibo.pegateway;

import com.mozu.api.contracts.paymentservice.extensibility.v1.*;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PeService implements IPeService {
    @Override
    public GatewayAuthorizeResponse authorize(GatewayAuthorizationRequest request) throws Exception {
        throw new Exception("Not implemented.");
    }

    @Override
    public GatewayCaptureResponse capture(CaptureRequest request) throws Exception {
        throw new Exception("Not implemented.");
    }

    @Override
    public GatewayCreditResponse credit(CaptureRequest request) throws Exception {
        throw new Exception("Not implemented.");
    }

    @Override
    public GatewayVoidResponse doVoid(CaptureRequest request) throws Exception {
        throw new Exception("Not implemented.");
    }

    @Override
    public GatewayCaptureResponse authorizeAndCapture(CaptureRequest request) throws Exception {
        throw new Exception("Not implemented.");
    }
}
