package com.melwaresystems.checklists_backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.melwaresystems.checklists_backend.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserDetails findByEmail(String email);

    Optional<UserModel> findById(UUID id);

    boolean existsByEmail(String email);

    void deleteById(UUID id);
}
