package com.melwaresystems.checklists_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melwaresystems.checklists_backend.models.TaskModel;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {

    Optional<TaskModel> findById(Long id);

    void deleteById(Long id);
}
