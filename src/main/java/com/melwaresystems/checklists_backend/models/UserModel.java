package com.melwaresystems.checklists_backend.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melwaresystems.checklists_backend.models.enums.Status;
import com.melwaresystems.checklists_backend.models.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER")
public class UserModel implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, unique = true)
  private UUID idUser;

  @Column(nullable = false, length = 60, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, length = 5)
  private Integer status;

  @Column(nullable = false, length = 10)
  private LocalDateTime dateCreated;

  @Column(nullable = false)
  private UserRole role;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_person")
  private PersonModel person;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<TaskListModel> taskLists = new ArrayList<>();

  public UserModel() {
  }

  public UserModel(UUID idUser, String email, String password, Status status, LocalDateTime dateCreated,
      PersonModel person, List<TaskListModel> taskLists) {
    this.idUser = idUser;
    this.email = email;
    setStatus(status);
    this.dateCreated = dateCreated;
    this.person = person;
    this.taskLists = taskLists;
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

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public void setDateCreated(LocalDateTime dateCreated) {
    this.dateCreated = dateCreated;
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.role == UserRole.ADMIN) {
      return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    } else {
      return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    // throw new UnsupportedOperationException("Unimplemented method
    // 'isAccountNonExpired'");
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // throw new UnsupportedOperationException("Unimplemented method
    // 'isAccountNonLocked'");
    return true;

  }

  @Override
  public boolean isCredentialsNonExpired() {
    // throw new UnsupportedOperationException("Unimplemented method
    // 'isCredentialsNonExpired'");
    return true;

  }

  @Override
  public boolean isEnabled() {
    // throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
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
    UserModel other = (UserModel) obj;
    if (idUser == null) {
      if (other.idUser != null)
        return false;
    } else if (!idUser.equals(other.idUser))
      return false;
    return true;
  }

}
