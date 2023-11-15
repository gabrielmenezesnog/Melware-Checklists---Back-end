package com.melwaresystems.checklists_backend.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.melwaresystems.checklists_backend.dto.TaskListDto;
import com.melwaresystems.checklists_backend.models.TaskListModel;
import com.melwaresystems.checklists_backend.repositories.TaskListRepository;
import com.melwaresystems.checklists_backend.services.exceptions.DatabaseException;
import com.melwaresystems.checklists_backend.services.exceptions.DuplicateEntryException;
import com.melwaresystems.checklists_backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TaskListService {

    @Autowired
    TaskListRepository taskListRepository;

    public List<TaskListModel> getAll() {
        return taskListRepository.findAll();
    }

    public TaskListModel findById(UUID id) {
        Optional<TaskListModel> taskList = taskListRepository.findById(id);
        return taskList.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<TaskListModel> findAllByIdTaskList(Iterable<UUID> id) {
        List<TaskListModel> taskList = taskListRepository.findAllById(id);
        return taskList;
    }

    @Transactional
    public TaskListModel createTaskList(TaskListModel taskList) {
        try {
            taskListRepository.save(taskList);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntryException();
        }
        return taskList;
    }

    public TaskListModel updateTask(TaskListModel taskList) {
        try {
            TaskListModel entity = taskListRepository.getReferenceById(taskList.getIdTaskList());
            updateData(entity, taskList);
            return taskListRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(taskList.getIdTaskList());
        }
    }

    private void updateData(TaskListModel entity, TaskListModel taskList) {
        if (taskList.getTitle() != null)
            entity.setTitle(taskList.getTitle());
    }

    public void deleteById(UUID id) {
        try {
            taskListRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public TaskListModel fromDTO(TaskListDto taskListDto) {
        return new TaskListModel(taskListDto.getIdTaskList(), taskListDto.getTitle(),
                taskListDto.getStatus(), taskListDto.getDateCreated());
    }
}
