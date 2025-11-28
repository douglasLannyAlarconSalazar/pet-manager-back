package com.petmanager.auth_service.web;

import com.petmanager.auth_service.config.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Public Key", description = "JWT public key endpoints for token verification")
public class PublicKeyController {

    private final JwtService jwtService;

    @GetMapping("/public-key")
    @Operation(
            summary = "Get JWT public key",
            description = "Retrieve the public key used for JWT token verification. Other services can use this key to validate tokens."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Public key retrieved successfully",
                    content = @Content
            )
    })
    public ResponseEntity<Map<String, String>> getPublicKey() {
        Map<String, String> response = new HashMap<>();
        response.put("publicKey", jwtService.getPublicKeyAsString());
        response.put("algorithm", "RS256");
        return ResponseEntity.ok(response);
    }
}

