package com.fluent.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "python.quiz")
public class PythonServerProperties {
    private String generateUrl;
    private String ttsUrl;
}