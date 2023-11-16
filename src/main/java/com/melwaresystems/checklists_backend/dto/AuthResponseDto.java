package com.melwaresystems.checklists_backend.dto;

import java.util.UUID;

import com.melwaresystems.checklists_backend.models.PersonModel;

public class AuthResponseDto {
    private UUID id;
    private String email;
    private PersonModel person;

    public AuthResponseDto(UUID id, String email, PersonModel person) {
        this.id = id;
        this.email = email;
        this.person = person;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PersonModel getPerson() {
        return person;
    }

    public void setPerson(PersonModel person) {
        this.person = person;
    }

}
