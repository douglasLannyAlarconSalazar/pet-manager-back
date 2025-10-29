package com.gestionCliente.notificaciones.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
public class TwilioConfig {
    
    private String accountSid;
    private String authToken;
    private String messagingServiceSid;
    
    // Getters and Setters
    public String getAccountSid() {
        return accountSid;
    }
    
    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }
    
    public String getAuthToken() {
        return authToken;
    }
    
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    
    public String getMessagingServiceSid() {
        return messagingServiceSid;
    }
    
    public void setMessagingServiceSid(String messagingServiceSid) {
        this.messagingServiceSid = messagingServiceSid;
    }
}
