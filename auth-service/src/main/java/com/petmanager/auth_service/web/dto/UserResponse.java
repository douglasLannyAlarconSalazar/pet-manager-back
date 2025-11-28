package com.petmanager.auth_service.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User information response")
public class UserResponse {

    @Schema(description = "User ID", example = "1")
    private Integer id;

    @Schema(description = "Username", example = "johndoe")
    private String username;

    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Role name", example = "USER")
    private String roleName;

    @Schema(description = "Role ID", example = "2")
    private Integer roleId;

    @Schema(description = "Whether the account is enabled", example = "true")
    private Boolean enabled;

    @Schema(description = "Account creation date", example = "2025-01-01")
    private LocalDate createdAt;

    @Schema(description = "Last access date", example = "2025-11-04")
    private LocalDate lastAccess;
}

