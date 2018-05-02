package com.kibo.pegateway.dto.override;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.mozu.api.contracts.paymentservice.extensibility.v1.AdapterContext;

public class AdapterContextOverrideSerializerModifier extends BeanSerializerModifier {
    @Override
    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        if (beanDesc.getBeanClass().isAssignableFrom(AdapterContext.class)) {
            return new AdapterContextOverrideSerializer((JsonSerializer<Object>) serializer);
        }
        return serializer;
    }
}
