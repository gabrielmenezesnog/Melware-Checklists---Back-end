package com.melwaresystems.checklists_backend.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.melwaresystems.checklists_backend.models.enums.UserStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER")
public class UserModel implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, unique = true)
  private UUID idUser;

  @Column(nullable = false, length = 60, unique = true)
  private String email;

  @Column(nullable = false, length = 40)
  private String password;

  @Column(nullable = false, length = 5)
  private Integer userStatus;

  @Column(nullable = false, length = 10)
  private LocalDateTime dateCreated;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_person")
  private PersonModel person;

  public UserModel() {
  }

  public UserModel(UUID id, String email, String password, UserStatus userStatus,
      LocalDateTime dateCreated, PersonModel person) {
    this.idUser = id;
    this.email = email;
    this.password = password;
    setUserStatus(userStatus);
    this.dateCreated = dateCreated;
    this.person = person;
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

  public UserStatus getUserStatus() {
    return UserStatus.valueOf(userStatus);
  }

  public void setUserStatus(UserStatus userStatus) {
    if (userStatus != null) {
      this.userStatus = userStatus.getCode();
    }
  }

  public LocalDateTime getDateCreated() {
    return dateCreated;
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
