package com.kibo.pegateway.dto.override;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashMap;

public class AdapterContextOverrideSerializer extends JsonSerializer<AdapterContextOverride> {
    private final JsonSerializer<Object> defaultSerializer;

    public AdapterContextOverrideSerializer(JsonSerializer<Object> defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    @Override
    public void serialize(AdapterContextOverride adapterContextOverride, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(adapterContextOverride.getSettingsMap() == null)
            adapterContextOverride.setSettingsMap(new HashMap<>());
        if(adapterContextOverride.getConfigurationMap() == null)
            adapterContextOverride.setConfigurationMap(new HashMap<>());
        adapterContextOverride.addPropertiesToSettings();
        adapterContextOverride.addPropertiesToConfiguration();
        defaultSerializer.serialize(adapterContextOverride, jsonGenerator, serializerProvider);
    }
}
