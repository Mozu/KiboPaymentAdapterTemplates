package com.kibo.pegateway.controller;

import com.kibo.pegateway.IPeService;
import com.kibo.pegateway.dto.override.base.IGatewayAuthorizeResponse;
import com.kibo.pegateway.dto.override.base.IGatewayTransactionResponse;
import com.mozu.api.contracts.paymentservice.extensibility.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;
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

    @Autowired
    BuildProperties buildProperties;

    @Autowired
    IPeService peService;

    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss.SSS");

    /**
     * Provides a lambda to implement only the functional requirements of the call.
     *
     * @param <RS> The response type for the call.
     */
    public interface HandleResponse<RS> {
        /**
         * Extend this to handle the call.
         *
         * Should throw an exception on any actual error,
         * which will be handled by the handleRequest method.
         *
         * @return The gateway response object corresponding to this method.
         * @throws Exception Thrown on any error.
         */
        RS handle() throws Exception;
    }

    public void cleanResponse(IGatewayTransactionResponse response) {
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
        if(IGatewayAuthorizeResponse.class.isAssignableFrom(response.getClass())) {
            IGatewayAuthorizeResponse temp = (IGatewayAuthorizeResponse)response;
            if (StringUtils.isEmpty(temp.getAuthCode())) {
                temp.setAuthCode("");
            }
            if (StringUtils.isEmpty(temp.getAvsCodes())) {
                temp.setAvsCodes("");
            }
            if (StringUtils.isEmpty(temp.getCvV2Codes())) {
                temp.setCvV2Codes("");
            }
        }
    }

    /**
     * Call this method with a lambda to handle the request.
     *
     * @param handler The lambda to call to handle the request.
     * @param responseClass The response class.  The call will create a response of this type to return an exception.
     * @param <RS> The response type.
     * @return Returns the response, either from the lambda, or creates and returns an error.
     * @throws IllegalAccessException Thrown on illegal access, converted to 500 by Spring.
     * @throws InstantiationException Thrown on instantiation exception, converted to 500 by Spring.
     */
    private <RS> RS handleRequest(HandleResponse<RS> handler, Class<?> responseClass) throws Exception {
        try {
            RS ret = handler.handle();
            cleanResponse((IGatewayTransactionResponse)ret);
            return ret;
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Calling vantiv", e);
            IGatewayTransactionResponse response = (IGatewayTransactionResponse) responseClass.newInstance();
            response.setIsDeclined(true);
            response.setRemoteConnectionStatus(ConnectionStatuses.Error);
            response.setResponseText(e.toString());
            return (RS) response;
        }
    }

    /**
     * Returns information about the currently deployed connector.
     *
     * NOTE: Maven compile has to be run in order for this to work.
     * If you are having weird errors around a bean called 'buildProperties',
     * drop to the commandline and run 'mvn clean package', which should
     * fix it.
     *
     * @return Returns a JSON string containing information about the build.
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "version")
    @ResponseBody
    public String getVersion() {
        String ret = "{\n";
        ret += "\t\"Group\": \"" + buildProperties.getGroup() + "\",\n";
        ret += "\t\"Name\": \"" + buildProperties.getName() + "\",\n";
        ret += "\t\"Artifact\": \"" + buildProperties.getArtifact() + "\",\n";
        ret += "\t\"Version\": \"" + buildProperties.getVersion() + "\",\n";
        ret += "\t\"Time\": \"" + buildProperties.getTime() + "\",\n";
        ret += "\t\"AsOf\": \"" + sdf.format(new Date()) + "\"\n";
        ret += "}\n";
        return ret;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/authorize")
    @ResponseBody
    public GatewayAuthorizeResponse authorize(@RequestBody GatewayAuthorizationRequest authorizeRequest) throws Exception {
        return handleRequest(() -> peService.authorize(authorizeRequest), GatewayAuthorizeResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/capture")
    @ResponseBody
    public GatewayCaptureResponse capture(@RequestBody CaptureRequest captureRequest) throws Exception {
        return handleRequest(() -> peService.capture(captureRequest), GatewayCaptureResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/credit")
    @ResponseBody
    public GatewayCreditResponse credit(@RequestBody CaptureRequest creditRequest) throws Exception {
        return handleRequest(() -> peService.credit(creditRequest), GatewayCreditResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/void")
    @ResponseBody
    public GatewayVoidResponse voidTransaction(@RequestBody CaptureRequest voidRequest) throws Exception {
        return handleRequest(() -> peService.doVoid(voidRequest), GatewayVoidResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/authorizeandcapture")
    @ResponseBody
    public GatewayCaptureResponse authorizeAndCapture(@RequestBody CaptureRequest authorizeAndCaptureRequest) throws Exception {
        return handleRequest(() -> peService.authorizeAndCapture(authorizeAndCaptureRequest), GatewayCaptureResponse.class);
    }
}
