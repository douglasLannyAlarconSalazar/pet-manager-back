package com.petmanager.auth_service.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private String roleName;
    private Integer roleId;
    private Boolean enabled;
    private LocalDate createdAt;
    private LocalDate lastAccess;
}

