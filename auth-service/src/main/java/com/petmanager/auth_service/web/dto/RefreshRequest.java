package com.petmanager.auth_service.web.dto;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}
