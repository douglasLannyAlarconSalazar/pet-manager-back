package com.petmanager.auth_service.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Authentication request with user credentials")
public class AuthRequest {

    @Schema(description = "Username or email address", example = "admin", required = true)
    private String usernameOrEmail;

    @Schema(description = "User password", example = "Admin@123", required = true)
    private String password;
}
