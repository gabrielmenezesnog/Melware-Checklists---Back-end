package com.melwaresystems.checklists_backend.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melwaresystems.checklists_backend.models.PersonModel;
import com.melwaresystems.checklists_backend.models.TaskListModel;
import com.melwaresystems.checklists_backend.models.UserModel;
import com.melwaresystems.checklists_backend.models.enums.Status;
import com.melwaresystems.checklists_backend.models.enums.UserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private UUID idUser;

    @NotBlank
    @Size(max = 60)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private Status status;

    @NotBlank
    private LocalDateTime dateCreated;

    @NotBlank
    private UserRole role;

    @NotBlank
    private PersonModel person;

    @JsonIgnore
    private List<TaskListModel> taskLists = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(UserModel user) {
        idUser = user.getIdUser();
        email = user.getEmail();
        password = user.getPassword();
        status = user.getStatus();
        dateCreated = user.getDateCreated();
        role = user.getRole();
        person = user.getPerson();
        taskLists = user.getTaskLists();
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getUserStatus() {
        return status;
    }

    public void setUserStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public PersonModel getPerson() {
        return person;
    }

    public void setPerson(PersonModel person) {
        this.person = person;
    }

    public List<TaskListModel> getTaskLists() {
        return taskLists;
    }

    public void setTaskLists(List<TaskListModel> taskLists) {
        this.taskLists = taskLists;
    }

}
