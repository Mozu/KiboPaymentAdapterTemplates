package com.kibo.pevantivgateway.controller;

import com.kibo.pevantivgateway.dto.common.AdapterContext;
import com.kibo.pevantivgateway.dto.request.AuthorizeRequest;
import com.kibo.pevantivgateway.dto.request.CaptureRequest;
import com.kibo.pevantivgateway.dto.request.PaymentRequest;
import com.kibo.pevantivgateway.dto.response.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/pevantiv")
/**
 * Controller to handle the calls.
 *
 * To implement, call 'handleRequest' with a 'HandleResponse' lambda.
 *
 * This reduces the amount of boilerplate and keeps the error handling in one place.
 *
 * The lambdas at the moment just throw an exception, which will cause the handleRequest
 * method to return a connection status of 'Error'.
 */
public class PeController {
    private static final Logger logger = Logger.getLogger(PeController.class.getSimpleName());

    public interface HandleResponse<RS extends GatewayResponse> {
        public RS handle() throws Exception;
    }


    private String fromConfiguration(PaymentRequest request, String name) throws Exception {
        String value = null;
        AdapterContext context = request.getContext();

        List<Map<String, String>> settingsMap = context.getSettings();
        if(settingsMap == null)
            throw new Exception("Settings missing.");
        for (Map<String, String> setting : settingsMap) {
            if (name.equalsIgnoreCase(setting.get("key"))) {
                value = setting.get("value");
                break;
            }
        }

        if (value == null) {
            throw new Exception("Missing configuration value '" + name + "'.");
        }
        return value;
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
