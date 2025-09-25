package com.petmanager.auth_service.web;

import com.petmanager.auth_service.config.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/hello")
public class TestController {

    @GetMapping("/public")
    public Map<String, String> publicEndpoint() {
        return Map.of("message", "This is a public endpoint - no authentication required");
    }

    @GetMapping("/private")
    public Map<String, Object> privateEndpoint(Authentication auth) {
        return Map.of(
                "message", "This is a protected endpoint - authentication required",
                "user", auth.getName(),
                "authorities", auth.getAuthorities()
        );
    }

    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal UserPrincipal principal) {
        return Map.of(
                "username", principal.getUsername(),
                "role", principal.getAuthorities().iterator().next().getAuthority(),
                "enabled", principal.isEnabled(),
                "userId", principal.getUser().getId()
        );
    }
}
