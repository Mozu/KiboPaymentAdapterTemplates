package com.kibo.pegateway.dto.override;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;

public class AdapterContextOverrideDeserializerModifier extends BeanDeserializerModifier {
    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        if (beanDesc.getBeanClass().isAssignableFrom(AdapterContextOverride.class)) {
            return new AdapterContextOverrideDeserializer((JsonDeserializer<Object>) deserializer);
        }
        return deserializer;
    }
}
