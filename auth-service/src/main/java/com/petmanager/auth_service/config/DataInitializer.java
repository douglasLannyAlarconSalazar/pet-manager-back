package com.petmanager.auth_service.config;

import com.petmanager.auth_service.entity.Role;
import com.petmanager.auth_service.entity.SystemUser;
import com.petmanager.auth_service.repository.RoleRepository;
import com.petmanager.auth_service.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final SystemUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;

    @Override
    @Transactional
    public void run(String... args) {
        try {
            initializeRoles();
            initializeAdminUser();
        } catch (Exception e) {
            log.error("Error during data initialization", e);
        }
    }

    private void initializeRoles() {
        log.info("Checking and initializing roles...");

        createRoleIfNotExists("ADMIN", "Administrator with full system access");
        createRoleIfNotExists("USER", "Regular user with limited access");
        createRoleIfNotExists("VETERINARY", "Veterinary professional");
        createRoleIfNotExists("CLIENT", "Pet owner client");

        log.info("Roles initialization completed");
    }

    private void createRoleIfNotExists(String roleName, String description) {
        roleRepository.findByName(roleName).ifPresentOrElse(
                role -> log.debug("Role '{}' already exists", roleName),
                () -> {
                    Role role = Role.builder()
                            .name(roleName)
                            .description(description)
                            .build();
                    roleRepository.save(role);
                    log.info("Created role: {}", roleName);
                }
        );
    }

    private void initializeAdminUser() {
        log.info("Checking admin user...");

        String adminUsername = adminProperties.getUsername();

        if (userRepository.existsByUsername(adminUsername)) {
            log.info("Admin user '{}' already exists, skipping creation", adminUsername);
            return;
        }

        Role adminRole = roleRepository.findByName(adminProperties.getRoleName())
                .orElseThrow(() -> new RuntimeException("ADMIN role not found. Please ensure roles are initialized first."));

        SystemUser adminUser = SystemUser.builder()
                .username(adminProperties.getUsername())
                .email(adminProperties.getEmail())
                .passwordHash(passwordEncoder.encode(adminProperties.getPassword()))
                .role(adminRole)
                .enabled(true)
                .createdAt(LocalDate.now())
                .build();

        userRepository.save(adminUser);

        log.info("========================================");
        log.info("Admin user created successfully!");
        log.info("Username: {}", adminProperties.getUsername());
        log.info("Email: {}", adminProperties.getEmail());
        log.info("Password: {} (Please change it after first login!)", adminProperties.getPassword());
        log.info("========================================");
    }
}

