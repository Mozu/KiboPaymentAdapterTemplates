package com.kibo.pegateway.dto.override;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class AdapterContextOverrideDeserializer extends JsonDeserializer<AdapterContextOverride> {
    private final JsonDeserializer<Object> defaultDeserializer;

    public AdapterContextOverrideDeserializer(JsonDeserializer<Object> defaultDeserializer) {
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public AdapterContextOverride deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        AdapterContextOverride ret = (AdapterContextOverride) defaultDeserializer.deserialize(jsonParser, deserializationContext, new AdapterContextOverride());
        try {
            ret.setPropertiesFromSettings();
            ret.setPropertiesFromConfiguration();
        }
        catch (Exception ex) {
            throw new IOException("Failed setting properties or configuration.", ex);
        }
        return ret;
    }
}
