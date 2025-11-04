package com.petmanager.auth_service.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Authentication response containing JWT tokens")
public class AuthResponse {

    @Schema(description = "JWT access token for API authentication", example = "eyJhbGciOiJSUzI1NiJ9...")
    private String accessToken;

    @Schema(description = "JWT refresh token for obtaining new access tokens", example = "eyJhbGciOiJSUzI1NiJ9...")
    private String refreshToken;

    @Schema(description = "Token type", example = "Bearer")
    private String tokenType;
}
