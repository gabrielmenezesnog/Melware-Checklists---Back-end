package com.melwaresystems.checklists_backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melwaresystems.checklists_backend.models.TaskListModel;

@Repository
public interface TaskListRepository extends JpaRepository<TaskListModel, UUID> {

    Optional<TaskListModel> findById(UUID id);

    void deleteById(UUID id);
}
