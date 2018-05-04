package com.kibo.pegateway.dto.override.base;

import com.kibo.pegateway.dto.override.common.AdapterContextOverride;
import com.kibo.pegateway.dto.override.request.CaptureRequestOverride;
import com.kibo.pegateway.dto.override.request.GatewayAuthorizationRequestOverride;
import com.kibo.pegateway.dto.override.response.GatewayAuthorizeResponseOverride;
import com.kibo.pegateway.dto.override.response.GatewayCaptureResponseOverride;
import com.kibo.pegateway.dto.override.response.GatewayCreditResponseOverride;
import com.kibo.pegateway.dto.override.response.GatewayVoidResponseOverride;
import com.mozu.api.contracts.paymentservice.extensibility.v1.*;

public enum ESerializers {
    AdapterContextOverrideSerializer(AdapterContext.class, AdapterContextOverride.class),
    CaptureRequestOverrideSerializer(CaptureRequest.class, CaptureRequestOverride.class),
    GatewayAuthorizationRequestOverrideSerializer(GatewayAuthorizationRequest.class, GatewayAuthorizationRequestOverride.class),
    GatewayAuthorizeResponseOverrideSerializer(GatewayAuthorizeResponse.class, GatewayAuthorizeResponseOverride.class),
    GatewayCaptureResponseOverrideSerializer(GatewayCaptureResponse.class, GatewayCaptureResponseOverride.class),
    GatewayCreditResponseOverrideSerializer(GatewayCreditResponse.class, GatewayCreditResponseOverride.class),
    GatewayVoidResponseOverrideSerializer(GatewayVoidResponse.class, GatewayVoidResponseOverride.class),
    DefaultSerializer(null, null);

    Class targetClass;
    Class resultClass;

    private ESerializers(Class targetClass, Class resultClass) {
        this.targetClass = targetClass;
        this.resultClass = resultClass;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public Class getResultClass() {
        return resultClass;
    }

    public static void overrideSerializer(Class targetClass, Class resultClass) throws Exception {
        boolean found = false;
        for (ESerializers serializer : ESerializers.values()) {
            if(serializer == DefaultSerializer)
                break;
            if(serializer.targetClass == targetClass) {
                serializer.resultClass = resultClass;
                found = true;
                break;
            }
        }
        if(!found)
            throw new Exception("No serializer found for class '"+targetClass.getName()+"'.");
    }

    public static ESerializers getDeserializer(Class test) {
        if (test == null) {
            return null;
        }
        for (ESerializers serializer : ESerializers.values()) {
            if(serializer == DefaultSerializer)
                break;
            if(serializer.resultClass == test)
                break;
            if (serializer.targetClass.isAssignableFrom(test)) {
                return serializer;
            }
        }
        return DefaultSerializer;
    }

    public static ESerializers getSerializer(Class test) {
        if (test == null) {
            return null;
        }
        for (ESerializers serializer : ESerializers.values()) {
            if(serializer == DefaultSerializer)
                break;
            if (serializer.targetClass.isAssignableFrom(test)) {
                return serializer;
            }
        }
        return DefaultSerializer;
    }
}
