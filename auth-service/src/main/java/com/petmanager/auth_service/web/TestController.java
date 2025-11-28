package com.petmanager.auth_service.web;

import com.petmanager.auth_service.config.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/hello")
@Tag(name = "Test Endpoints", description = "Test endpoints for authentication verification and development")
public class TestController {

    @GetMapping("/public")
    @Operation(
            summary = "Public endpoint",
            description = "Test endpoint that doesn't require authentication. Returns a simple message."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = @Content
            )
    })
    public Map<String, String> publicEndpoint() {
        return Map.of("message", "This is a public endpoint - no authentication required");
    }

    @GetMapping("/private")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Protected endpoint",
            description = "Test endpoint that requires authentication. Returns user information and authorities."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response with user information",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content
            )
    })
    public Map<String, Object> privateEndpoint(Authentication auth) {
        return Map.of(
                "message", "This is a protected endpoint - authentication required",
                "user", auth.getName(),
                "authorities", auth.getAuthorities()
        );
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get current user",
            description = "Get detailed information about the currently authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response with current user details",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Authentication required",
                    content = @Content
            )
    })
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal UserPrincipal principal) {
        return Map.of(
                "username", principal.getUsername(),
                "role", principal.getAuthorities().iterator().next().getAuthority(),
                "enabled", principal.isEnabled(),
                "userId", principal.getUser().getId()
        );
    }
}
