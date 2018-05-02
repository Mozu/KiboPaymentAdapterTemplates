package com.kibo.pegateway.dto.override;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mozu.api.contracts.paymentservice.extensibility.v1.AdapterContext;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AdapterContextOverride extends AdapterContext {
    @Getter
    @Setter
    @JsonSerialize(using = KeyValueTupleSerializer.class)
    @JsonDeserialize(using = KeyValueTupleDeserializer.class)
    @JsonProperty("settings")
    Map<String, Object> settingsMap;
    @Getter
    @Setter
    @JsonSerialize(using = KeyValueTupleSerializer.class)
    @JsonDeserialize(using = KeyValueTupleDeserializer.class)
    @JsonProperty("configuration")
    Map<String, Object> configurationMap;
    @Getter
    @Setter
    @JsonIgnore
    transient String testingUrl;
    @Getter
    @Setter
    @JsonIgnore
    transient String productionUrl;

    protected String getStringFromMap(Map<String, Object> map, String name, String mapName) throws Exception {
        Object o = map.get(name);
        if (o == null || !(o instanceof String)) {
            throw new Exception("Invalid '" + name + "' object in '" + mapName + "' in adapter context: '" + o + "'.");
        }
        return (String) o;
    }

    protected void addStringToMap(String name, String value, Map<String, Object> map, String mapName) {
        map.put(name, value);
    }

    protected void setPropertiesFromSettings() throws Exception {
    }

    protected void setPropertiesFromConfiguration() throws Exception {
        testingUrl = getStringFromMap(configurationMap, "testingUrl", "configuration");
        productionUrl = getStringFromMap(configurationMap, "productionUrl", "configuration");
    }

    void addPropertiesToSettings() {
    }

    void addPropertiesToConfiguration() {
        addStringToMap("testingUrl", testingUrl, configurationMap, "configuration");
        addStringToMap("productionUrl", productionUrl, configurationMap, "configuration");
    }
}
