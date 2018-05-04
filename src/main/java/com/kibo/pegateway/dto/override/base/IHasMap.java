package com.kibo.pegateway.dto.override.base;

import org.springframework.util.StringUtils;

import java.util.Map;

public interface IHasMap {
    public static String getStringFromMap(Map<String, Object> map, String name, boolean required, String mapName) throws Exception {
        Object o = map.get(name);
        if (o == null || !(o instanceof String) || StringUtils.isEmpty((String) o)) {
            if (required) {
                throw new Exception("Invalid '" + name + "' object in '" + mapName + "' in adapter context: '" + o + "'.");
            }
        }
        return (String) o;
    }

    public static void addStringToMap(String name, String value, Map<String, Object> map, String mapName) {
        map.put(name, value);
    }

    public static Long getLongFromMap(Map<String, Object> map, String name, boolean required, String mapName) throws Exception {
        Object o = map.get(name);
        if (o == null) {
            if (required) {
                throw new Exception("Missing '" + name + "' from '" + mapName + "'.");
            }
            else {
                return null;
            }
        }
        if(o instanceof Long)
            return (Long)o;
        try {
            return Long.valueOf(o.toString());
        } catch (Exception ex) {
            throw new Exception("Invalid non-numeric '"+name+"' from '"+mapName+"'.");
        }
    }

    public void setPropertiesFromMap() throws Exception;

    public void addPropertiesToMap();
}
