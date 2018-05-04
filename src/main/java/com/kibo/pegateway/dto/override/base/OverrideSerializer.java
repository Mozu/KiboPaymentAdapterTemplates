package com.kibo.pegateway.dto.override.base;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class OverrideSerializer<R extends Object> extends JsonSerializer<R> {
    JsonSerializer<Object> defaultSerializer;

    public void init(JsonSerializer<Object> defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    @Override
    public void serialize(R target, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            preProcess(target);
        }
        catch (Exception e) {
            throw new IOException("Error preprocessing for class '" + target.getClass().getName() + "'.", e);
        }
        defaultSerializer.serialize(target, jsonGenerator, serializerProvider);
    }

    protected void preProcess(R object) throws Exception {
        if(IHasMap.class.isAssignableFrom(object.getClass())) {
            ((IHasMap)object).addPropertiesToMap();
        }
    }
}
