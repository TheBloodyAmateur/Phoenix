package com.github.thebloodyamateur.phoenix.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "security")
@Setter
@Getter
public class SecurityProperties {
    private List<String> publicEndpoints;
}
