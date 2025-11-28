package com.petmanager.auth_service.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request to refresh access token")
public class RefreshRequest {

    @Schema(description = "Valid JWT refresh token", example = "eyJhbGciOiJSUzI1NiJ9...", required = true)
    private String refreshToken;
}
