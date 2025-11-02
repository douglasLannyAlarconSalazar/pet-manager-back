package com.gestionCliente.gestion_cliente.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtVerificationService {

    @Value("${auth.service.public-key-url:http://localhost:8080/auth/public-key}")
    private String publicKeyUrl;

    private PublicKey publicKey;

    @PostConstruct
    public void init() throws Exception {
        fetchPublicKeyFromAuthService();
    }

    private void fetchPublicKeyFromAuthService() throws Exception {
        // In production, you'd use RestTemplate or WebClient
        // For simplicity, we'll use a basic HTTP connection
        java.net.URL url = new java.net.URL(publicKeyUrl);
        java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        if (connection.getResponseCode() == 200) {
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response manually (simple approach)
            String jsonResponse = response.toString();
            String publicKeyString = extractPublicKeyFromJson(jsonResponse);

            // Convert Base64 encoded public key to PublicKey object
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

            System.out.println("Successfully fetched and loaded public key from auth-service");
        } else {
            throw new RuntimeException("Failed to fetch public key from auth-service. Status: " + connection.getResponseCode());
        }
    }

    private String extractPublicKeyFromJson(String json) {
        // Simple JSON parsing for {"publicKey":"...", "algorithm":"..."}
        String key = "\"publicKey\":\"";
        int startIndex = json.indexOf(key) + key.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token) {
        try {
            boolean isValid = !isTokenExpired(token);
            if (isValid) {
                System.out.println("Token is valid for user: " + extractUsername(token));
            } else {
                System.out.println("Token is expired");
            }
            return isValid;
        } catch (Exception e) {
            System.err.println("Token validation error: " + e.getClass().getName() + " - " + e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

