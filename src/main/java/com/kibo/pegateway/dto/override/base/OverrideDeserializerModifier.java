package com.kibo.pegateway.dto.override.base;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OverrideDeserializerModifier extends BeanDeserializerModifier {
    static Logger logger = Logger.getLogger(OverrideDeserializerModifier.class.getSimpleName());

    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        Class clazz = beanDesc.getBeanClass();
        ESerializers serializer = ESerializers.getDeserializer(clazz);
        if (serializer == ESerializers.DefaultSerializer) {
            return deserializer;
        }
        try {
            OverrideDeserializer overrideDeserializer = new OverrideDeserializer();
            overrideDeserializer.init(deserializer, serializer.getResultClass());
            return overrideDeserializer;
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Missing or invalid deserializer.", e);
            return deserializer;
        }
    }
}
