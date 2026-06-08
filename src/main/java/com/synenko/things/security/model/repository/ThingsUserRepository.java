package com.synenko.things.security.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.synenko.things.security.model.entity.ThingsUser;

import java.util.Optional;

@Repository
public interface ThingsUserRepository extends JpaRepository<ThingsUser, Long> {
    Optional<ThingsUser> findByUsername(String username);
    Optional<ThingsUser> findByEmail(String email);
}
