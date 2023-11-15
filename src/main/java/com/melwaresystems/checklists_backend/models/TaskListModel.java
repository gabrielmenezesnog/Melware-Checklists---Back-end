package com.melwaresystems.checklists_backend.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.melwaresystems.checklists_backend.models.enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TASK_LIST")
public class TaskListModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, unique = true)
  private UUID idTaskList;

  @Column(nullable = false, length = 60)
  private String title;

  @Column(nullable = false, length = 5)
  private Integer status;

  @Column(nullable = false, length = 10)
  private LocalDateTime dateCreated;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskList", cascade = CascadeType.ALL)
  private List<TaskModel> tasks = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "id_user", nullable = false)
  @JsonBackReference
  private UserModel user;

  public TaskListModel() {
  }

  public TaskListModel(UUID idTaskList, String title, Status status, LocalDateTime dateCreated) {
    this.idTaskList = idTaskList;
    this.title = title;
    setStatus(status);
    this.dateCreated = dateCreated;
  }

  public TaskListModel(UUID idTaskList, String title, Integer status, LocalDateTime dateCreated, List<TaskModel> tasks,
      UserModel user) {
    this.idTaskList = idTaskList;
    this.title = title;
    this.status = status;
    this.dateCreated = dateCreated;
    this.tasks = tasks;
    this.user = user;
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

  public List<TaskModel> getTasks() {
    return tasks;
  }

  public void setTasks(List<TaskModel> tasks) {
    this.tasks = tasks;
  }

  public UserModel getUser() {
    return user;
  }

  public void setUser(UserModel user) {
    this.user = user;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idTaskList == null) ? 0 : idTaskList.hashCode());
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
    TaskListModel other = (TaskListModel) obj;
    if (idTaskList == null) {
      if (other.idTaskList != null)
        return false;
    } else if (!idTaskList.equals(other.idTaskList))
      return false;
    return true;
  }

}
