package com.melwaresystems.checklists_backend.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.melwaresystems.checklists_backend.models.TaskListModel;
import com.melwaresystems.checklists_backend.models.TaskModel;
import com.melwaresystems.checklists_backend.models.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private Long idTask;

    @NotBlank
    @Size(max = 60)
    private String title;

    @NotBlank
    @Size(max = 400)
    private String description;

    @NotBlank
    private Integer order;

    private Status status;

    @NotBlank
    private LocalDateTime dateCreated;

    @NotBlank
    private TaskListModel taskList;

    public TaskDto() {
    }

    public TaskDto(TaskModel task) {
        idTask = task.getIdTask();
        title = task.getTitle();
        description = task.getDescription();
        order = task.getOrder();
        status = task.getStatus();
        dateCreated = task.getDateCreated();
        taskList = task.getTaskList();
    }

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public TaskListModel getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskListModel taskList) {
        this.taskList = taskList;
    }

}
