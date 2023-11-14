package com.melwaresystems.checklists_backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melwaresystems.checklists_backend.models.ContactModel;

@Repository
public interface ContactRepository extends JpaRepository<ContactModel, UUID> {
    Optional<ContactModel> findById(UUID id);
}
