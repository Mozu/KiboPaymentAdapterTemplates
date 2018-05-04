package com.kibo.pegateway.dto.override.base;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class OverrideDeserializer<R extends Object> extends JsonDeserializer<R> {
    JsonDeserializer<Object> defaultDeserializer;
    Class targetClass;

    public void init(JsonDeserializer<Object> defaultDeserializer, Class targetClass) {
        this.defaultDeserializer = defaultDeserializer;
        this.targetClass = targetClass;
    }

    @Override
    public R deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
            String json = jsonParser.readValueAsTree().toString();
            R ret = (R)objectMapper.readValue(json, (Class<Object>) targetClass);
            postProcess(ret);
            return ret;
        }
        catch (Exception ex) {
            throw new IOException("Failed deserializing.", ex);
        }
    }

    protected void postProcess(R object) throws Exception {
        if(IHasMap.class.isAssignableFrom(object.getClass())) {
            ((IHasMap)object).setPropertiesFromMap();
        }
    }
}
