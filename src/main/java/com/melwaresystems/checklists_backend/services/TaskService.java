package com.melwaresystems.checklists_backend.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.melwaresystems.checklists_backend.dto.TaskDto;
import com.melwaresystems.checklists_backend.models.TaskModel;
import com.melwaresystems.checklists_backend.repositories.TaskRepository;
import com.melwaresystems.checklists_backend.services.exceptions.DatabaseException;
import com.melwaresystems.checklists_backend.services.exceptions.DuplicateEntryException;
import com.melwaresystems.checklists_backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public List<TaskModel> getAll() {
        return taskRepository.findAll();
    }

    public TaskModel findById(Long id) {
        Optional<TaskModel> user = taskRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<TaskModel> findAllByIdTaskList(Iterable<Long> id) {
        List<TaskModel> taskList = taskRepository.findAllById(id);
        return taskList;
    }

    @Transactional
    public TaskModel createTask(TaskModel task) {
        try {
            taskRepository.save(task);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntryException();
        }
        return task;
    }

    public TaskModel updateTask(TaskModel task) {
        try {
            TaskModel entity = taskRepository.getReferenceById(task.getIdTask());
            updateData(entity, task);
            return taskRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(task.getIdTask());
        }
    }

    private void updateData(TaskModel entity, TaskModel task) {
        if (task.getTitle() != null)
            entity.setTitle(task.getTitle());
        if (task.getDescription() != null)
            entity.setDescription(task.getDescription());
        if (task.getOrder() != null)
            entity.setOrder(task.getOrder());
    }

    public void deleteById(Long id) {
        try {
            taskRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public TaskModel fromDTO(TaskDto taskDto) {
        return new TaskModel(taskDto.getIdTask(), taskDto.getTitle(), taskDto.getDescription(), taskDto.getOrder(),
                taskDto.getStatus(), taskDto.getDateCreated(), taskDto.getTaskList());
    }
}
