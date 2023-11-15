package com.melwaresystems.checklists_backend.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.melwaresystems.checklists_backend.models.TaskListModel;
import com.melwaresystems.checklists_backend.models.TaskModel;
import com.melwaresystems.checklists_backend.models.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskListDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private UUID idTaskList;

    @NotBlank
    @Size(max = 60)
    private String title;

    @NotBlank
    private Status status;

    @NotBlank
    private LocalDateTime dateCreated;

    private List<TaskModel> tasks = new ArrayList<>();

    public TaskListDto() {
    }

    public TaskListDto(TaskListModel taskList) {
        idTaskList = taskList.getIdTaskList();
        title = taskList.getTitle();
        status = taskList.getStatus();
        dateCreated = taskList.getDateCreated();
        tasks = taskList.getTasks();
    }

    public UUID getIdTaskList() {
        return idTaskList;
    }

    public void setIdTaskList(UUID idTaskList) {
        this.idTaskList = idTaskList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<TaskModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskModel> tasks) {
        this.tasks = tasks;
    }

}
