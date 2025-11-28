package com.petmanager.auth_service.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to update user information. All fields are optional.")
public class UserUpdateRequest {

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "New username", example = "johndoe_updated", minLength = 3, maxLength = 50)
    private String username;

    @Email(message = "Email should be valid")
    @Schema(description = "New email address", example = "john.updated@example.com")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "New password", example = "NewSecurePass123", minLength = 6)
    private String password;

    @Schema(description = "New role ID", example = "2")
    private Integer roleId;

    @Schema(description = "Enable or disable the account", example = "true")
    private Boolean enabled;
}

