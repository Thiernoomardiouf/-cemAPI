package com.cybersourcecem.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Getter
@Component
public class CyberSourceProperties {

    // getters...
    @Value("${cybersource.runEnvironment:apitest.cybersource.com}")
    private String runEnvironment;

    @Value("${cybersource.merchant_id}")
    private String merchantID;

    @Value("${cybersource.api_key_id:}")
    private String apiKeyId;

    @Value("${cybersource.secret_key:}")
    private String secretKey;

    public Properties toProperties() {
        Properties props = new Properties();
        props.setProperty("merchantID", merchantID);
        props.setProperty("runEnvironment", runEnvironment);
        props.setProperty("authenticationType", "http_signature"); // ou jwt si tu utilises p12
        props.setProperty("merchantKeyId", apiKeyId);
        props.setProperty("merchantsecretKey", secretKey);
        return props;
    }
}
