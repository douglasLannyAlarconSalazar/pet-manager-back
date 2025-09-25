package com.petmanager.auth_service.repository;

import com.petmanager.auth_service.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer> {
    Optional<SystemUser> findByUsername(String username);

    Optional<SystemUser> findByEmail(String email);

    @Query("SELECT u FROM SystemUser u WHERE u.username = :username OR u.email = :email")
    Optional<SystemUser> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
