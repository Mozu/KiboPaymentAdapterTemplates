package com.kibo.pegateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kibo.pegateway.dto.override.AdapterContextOverride;
import com.kibo.pegateway.dto.override.AdapterContextOverrideDeserializerModifier;
import com.kibo.pegateway.dto.override.AdapterContextOverrideSerializerModifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.setSerializerModifier(new AdapterContextOverrideSerializerModifier());
        simpleModule.setDeserializerModifier(new AdapterContextOverrideDeserializerModifier());
        builder.modules(simpleModule);
        return builder;
    }
}
