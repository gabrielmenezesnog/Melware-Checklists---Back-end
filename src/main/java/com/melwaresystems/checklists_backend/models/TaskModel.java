package com.melwaresystems.checklists_backend.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.melwaresystems.checklists_backend.models.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TASK")
public class TaskModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long idTask;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(nullable = false, length = 400)
    private String description;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "`order`")
    private Integer order;

    @Column(nullable = false, length = 5)
    private Integer status;

    @Column(nullable = false, length = 10)
    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "id_taskList", nullable = false)
    @JsonBackReference
    private TaskListModel taskList;

    public TaskModel() {
    }

    public TaskModel(Long idTask, String title, String description, Integer order, Status status,
            LocalDateTime dateCreated, TaskListModel taskList) {
        this.idTask = idTask;
        this.title = title;
        this.description = description;
        this.order = order;
        setStatus(status);
        this.dateCreated = dateCreated;
        this.taskList = taskList;
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
        return Status.valueOf(status);
    }

    public void setStatus(Status status) {
        if (status != null) {
            this.status = status.getCode();
        }
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idTask == null) ? 0 : idTask.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TaskModel other = (TaskModel) obj;
        if (idTask == null) {
            if (other.idTask != null)
                return false;
        } else if (!idTask.equals(other.idTask))
            return false;
        return true;
    }

}
