package com.kibo.pegateway.dto.override;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kibo.pegateway.config.JacksonConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = "com.kibo")
@SpringBootTest
@Import(JacksonConfig.class)
@Component
public class TestAdapterContextOverride {
    @Autowired
    ObjectMapper objectMapper;

    static String testUrl = "thisisatesturl";
    static String prodUrl = "thisisaprodurl";
    static String expectedJson = "{\"configuration\":[{\"key\":\"testingUrl\",\"value\":\"thisisatesturl\"},{\"key\":\"productionUrl\",\"value\":\"thisisaprodurl\"}],\"settings\":[]}";

    @Test
    public void testSerialize() throws JsonProcessingException {
        AdapterContextOverride adapterContextOverride = new AdapterContextOverride();
        adapterContextOverride.setTestingUrl(testUrl);
        adapterContextOverride.setProductionUrl(prodUrl);
        String value = objectMapper.writeValueAsString(adapterContextOverride);
        assert (expectedJson.equals(value));
    }

    @Test
    public void testDeserialize() throws IOException {
        AdapterContextOverride adapterContextOverride = objectMapper.readValue(expectedJson, AdapterContextOverride.class);
        assert(testUrl.equals(adapterContextOverride.getTestingUrl()));
        assert(prodUrl.equals(adapterContextOverride.getProductionUrl()));
    }
}
