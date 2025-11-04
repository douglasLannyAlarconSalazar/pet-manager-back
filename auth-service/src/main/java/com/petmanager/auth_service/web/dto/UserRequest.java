package com.petmanager.auth_service.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to create a new user")
public class UserRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "Unique username", example = "johndoe", required = true, minLength = 3, maxLength = 50)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Schema(description = "Valid email address", example = "john.doe@example.com", required = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "User password", example = "SecurePass123", required = true, minLength = 6)
    private String password;

    @Schema(description = "Role ID to assign to the user", example = "2")
    private Integer roleId;

    @Schema(description = "Whether the user account is enabled", example = "true", defaultValue = "true")
    private Boolean enabled;
}

