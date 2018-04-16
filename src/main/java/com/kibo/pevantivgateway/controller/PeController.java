package com.kibo.pevantivgateway.controller;

import com.kibo.pevantivgateway.dto.request.AuthorizeRequest;
import com.kibo.pevantivgateway.dto.request.CaptureRequest;
import com.kibo.pevantivgateway.dto.request.PaymentRequest;
import com.kibo.pevantivgateway.dto.response.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/pevantiv")
public class PeController {
    private static final Logger logger = Logger.getLogger(PeController.class.getSimpleName());

    public interface HandleResponse<RS extends GatewayResponse> {
        public RS handle() throws Exception;
    }

    private <RS extends GatewayResponse> RS handleRequest(PaymentRequest request, HandleResponse<RS> handler, Class<?> responseClass) throws IllegalAccessException, InstantiationException {
        try {
            return handler.handle();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Calling gateway", e);
            GatewayResponse response = (GatewayResponse) responseClass.newInstance();
            response.setIsDeclined(true);
            response.setRemoteConnectionStatus(GatewayResponse.ConnectionStatuses.Error);
            response.setResponseText(e.toString());
            return (RS) response;
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/authorize")
    @ResponseBody
    public AuthorizeGatewayResponse authorize(AuthorizeRequest authorizeRequest) throws Exception {
        return handleRequest(authorizeRequest, () -> {
            throw new Exception("Unhandled");
        }, AuthorizeGatewayResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/capture")
    @ResponseBody
    public CaptureGatewayResponse capture(CaptureRequest captureRequest) throws Exception {
        return handleRequest(captureRequest, () -> {
            throw new Exception("Unhandled");
        }, CaptureGatewayResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/credit")
    @ResponseBody
    public CreditGatewayResponse credit(CaptureRequest creditRequest) throws Exception {
        return handleRequest(creditRequest, () -> {
            throw new Exception("Unhandled");
        }, CreditGatewayResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/void")
    @ResponseBody
    public VoidGatewayResponse voidTransaction(CaptureRequest voidRequest) throws Exception {
        return handleRequest(voidRequest, () -> {
            throw new Exception("Unhandled");
        }, VoidGatewayResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/authorizeandcapture")
    @ResponseBody
    public CaptureGatewayResponse authorizeAndCapture(CaptureRequest authorizeAndCaptureRequest) throws Exception {
        return handleRequest(authorizeAndCaptureRequest, () -> {
            throw new Exception("Unhandled");
        }, CaptureGatewayResponse.class);
    }
}
