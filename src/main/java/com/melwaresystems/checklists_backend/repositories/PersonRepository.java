package com.melwaresystems.checklists_backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melwaresystems.checklists_backend.models.PersonModel;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel, UUID> {
    Optional<PersonModel> findById(UUID id);
}
