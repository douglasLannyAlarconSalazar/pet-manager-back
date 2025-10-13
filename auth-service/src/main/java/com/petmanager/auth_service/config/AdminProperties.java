package com.petmanager.auth_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.admin")
@Data
public class AdminProperties {
    private String username;
    private String email;
    private String password;
    private String roleName;
}
