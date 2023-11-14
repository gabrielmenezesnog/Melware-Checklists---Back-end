package com.melwaresystems.checklists_backend.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import com.melwaresystems.checklists_backend.models.ContactModel;
import com.melwaresystems.checklists_backend.models.PersonModel;

import jakarta.validation.constraints.NotBlank;

public class PersonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private UUID idPerson;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    private String cpf;

    private LocalDate birthDate;

    @NotBlank
    private ContactModel contact;

    public PersonDto() {
    }

    public PersonDto(PersonModel person) {
        idPerson = person.getIdPerson();
        name = person.getName();
        lastName = person.getLastName();
        cpf = person.getCpf();
        birthDate = person.getBirthDate();
        contact = person.getContact();
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

}
