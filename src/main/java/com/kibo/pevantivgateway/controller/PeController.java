package com.kibo.pevantivgateway.controller;

import com.kibo.pevantivgateway.dto.common.AdapterContext;
import com.kibo.pevantivgateway.dto.common.KeyValueTuple;
import com.kibo.pevantivgateway.dto.request.AuthorizeRequest;
import com.kibo.pevantivgateway.dto.request.CaptureRequest;
import com.kibo.pevantivgateway.dto.request.PaymentRequest;
import com.kibo.pevantivgateway.dto.response.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
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

    /**
     * Used to format dates in the version call.
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss.SSS");

    /**
     * Get a value from the configuration in the payment request.
     *
     * @param request The payment request to use.
     * @param name The name of the value to get out of the payment request configuration.
     * @return The value, if any.
     * @throws Exception Thrown if any of the pieces are missing needed to get the value.
     */
    private String fromConfiguration(PaymentRequest request, String name) throws Exception {
        String value = null;
        AdapterContext context = request.getContext();

        List<KeyValueTuple> configurationMap = context.getConfiguration();
        if (configurationMap == null) {
            throw new Exception("Settings missing.");
        }
        for (KeyValueTuple setting : configurationMap) {
            if (name.equalsIgnoreCase(setting.getKey())) {
                Object o = setting.getValue();
                if (o == null) {
                    throw new Exception("Missing configuration value '" + name + "'.");
                }
                value = "" + setting.getValue();
                break;
            }
        }
        return value;
    }

    /**
     * Get a value from the settings in the payment request.
     *
     * @param request The payment request to use.
     * @param name The name of the value to get out of the payment request settings.
     * @return The value, if any.
     * @throws Exception Thrown if any of the pieces are missing needed to get the value.
     */
    private String fromSettings(PaymentRequest request, String name) throws Exception {
        String value = null;
        AdapterContext context = request.getContext();

        List<Map<String, String>> settingsMap = context.getSettings();
        if (settingsMap == null) {
            throw new Exception("Settings missing.");
        }
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

    /**
     * Provides a lambda to implement only the functional requirements of the call.
     *
     * @param <RS> The response type for the call.
     */
    public interface HandleResponse<RS extends GatewayResponse> {
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
    private <RS extends GatewayResponse> RS handleRequest(PaymentRequest request, HandleResponse<RS> handler, Class<?> responseClass) throws IllegalAccessException, InstantiationException {
        try {
            RS ret = handler.handle();
            if(ret.getIsDeclined() && ret.getRemoteConnectionStatus() == GatewayResponse.ConnectionStatuses.Success)
                ret.setRemoteConnectionStatus(GatewayResponse.ConnectionStatuses.Reject);
            return ret;
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Calling vantiv", e);
            GatewayResponse response = (GatewayResponse) responseClass.newInstance();
            response.setIsDeclined(true);
            response.setRemoteConnectionStatus(GatewayResponse.ConnectionStatuses.Error);
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
    public AuthorizeGatewayResponse authorize(@RequestBody AuthorizeRequest authorizeRequest) throws Exception {
        return handleRequest(authorizeRequest, () -> {
            throw new Exception("Not implemented.");
        }, AuthorizeGatewayResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/capture")
    @ResponseBody
    public CaptureGatewayResponse capture(@RequestBody CaptureRequest captureRequest) throws Exception {
        return handleRequest(captureRequest, () -> {
            throw new Exception("Not implemented.");
        }, CaptureGatewayResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/credit")
    @ResponseBody
    public CreditGatewayResponse credit(@RequestBody CaptureRequest creditRequest) throws Exception {
        return handleRequest(creditRequest, () -> {
            throw new Exception("Not implemented.");
        }, CreditGatewayResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/void")
    @ResponseBody
    public VoidGatewayResponse voidTransaction(@RequestBody CaptureRequest voidRequest) throws Exception {
        return handleRequest(voidRequest, () -> {
            throw new Exception("Not implemented.");
        }, VoidGatewayResponse.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", value = "/authorizeandcapture")
    @ResponseBody
    public CaptureGatewayResponse authorizeAndCapture(@RequestBody CaptureRequest authorizeAndCaptureRequest) throws Exception {
        return handleRequest(authorizeAndCaptureRequest, () -> {
            throw new Exception("Not implemented.");
        }, CaptureGatewayResponse.class);
    }
}
