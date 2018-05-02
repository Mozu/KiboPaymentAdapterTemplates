package com.kibo.pegateway.controller;

import com.kibo.pegateway.IPeService;
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
public abstract class PeController implements IPeController {
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
    public interface HandleResponse<RS extends Object> {
        /**
         * Extend this to handle the call.
         *
         * Should throw an exception on any actual error,
         * which will be handled by the handleRequest method.
         *
         * @return The gateway response object corresponding to this method.
         * @throws Exception Thrown on any error.
         */
        public RS handle() throws Exception;
    }

    /**
     * Call this method with a lambda to handle the request.
     *
     * @param request The request object to handle.
     * @param handler The lambda to call to handle the request.
     * @param responseClass The response class.  The call will create a response of this type to return an exception.
     * @param <RS> The response type.
     * @return Returns the response, either from the lambda, or creates and returns an error.
     * @throws IllegalAccessException Thrown on illegal access, converted to 500 by Spring.
     * @throws InstantiationException Thrown on instantiation exception, converted to 500 by Spring.
     */
    private <RS extends Object> RS handleRequest(Object request, HandleResponse<RS> handler, Class<?> responseClass) throws Exception {
        try {
            RS ret = handler.handle();
            EResponseCleaners.cleanResponse(ret);
            return ret;
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Calling vantiv", e);
            Object response = (Object) responseClass.newInstance();
            EResponseCleaners.errorResponse(response, e);
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
        return handleRequest(authorizeRequest, () -> {
            return peService.authorize(authorizeRequest);
        }, GatewayAuthorizeResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/capture")
    @ResponseBody
    public GatewayCaptureResponse capture(@RequestBody CaptureRequest captureRequest) throws Exception {
        return handleRequest(captureRequest, () -> {
            return peService.capture(captureRequest);
        }, GatewayCaptureResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/credit")
    @ResponseBody
    public GatewayCreditResponse credit(@RequestBody CaptureRequest creditRequest) throws Exception {
        return handleRequest(creditRequest, () -> {
            return peService.credit(creditRequest);
        }, GatewayCreditResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/void")
    @ResponseBody
    public GatewayVoidResponse voidTransaction(@RequestBody CaptureRequest voidRequest) throws Exception {
        return handleRequest(voidRequest, () -> {
            return peService.doVoid(voidRequest);
        }, GatewayVoidResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/authorizeandcapture")
    @ResponseBody
    public GatewayCaptureResponse authorizeAndCapture(@RequestBody CaptureRequest authorizeAndCaptureRequest) throws Exception {
        return handleRequest(authorizeAndCaptureRequest, () -> {
            return peService.authorizeAndCapture(authorizeAndCaptureRequest);
        }, GatewayCaptureResponse.class);
    }
}
