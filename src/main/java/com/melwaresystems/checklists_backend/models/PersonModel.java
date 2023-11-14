package com.melwaresystems.checklists_backend.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name = "PERSON")
public class PersonModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private UUID idPerson;
    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 60)
    private String lastName;

    @Column(length = 12, unique = true)
    private String cpf;

    @Column(length = 10)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    private LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contact")
    private ContactModel contact;

    public PersonModel() {
    }

    public PersonModel(UUID idPerson, String name, String lastName, ContactModel contact) {
        this.idPerson = idPerson;
        this.name = name;
        this.lastName = lastName;
        this.contact = contact;
    }

    public PersonModel(UUID idPerson, String name, String lastName, String cpf, LocalDate birthDate,
            ContactModel contact) {
        this.idPerson = idPerson;
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.contact = contact;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UUID getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(UUID idPerson) {
        this.idPerson = idPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public ContactModel getContact() {
        return contact;
    }

    public void setContact(ContactModel contact) {
        this.contact = contact;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPerson == null) ? 0 : idPerson.hashCode());
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
        PersonModel other = (PersonModel) obj;
        if (idPerson == null) {
            if (other.idPerson != null)
                return false;
        } else if (!idPerson.equals(other.idPerson))
            return false;
        return true;
    }

}
