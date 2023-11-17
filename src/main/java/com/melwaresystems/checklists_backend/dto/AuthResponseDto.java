package com.melwaresystems.checklists_backend.dto;

import java.util.UUID;

import com.melwaresystems.checklists_backend.models.PersonModel;

public class AuthResponseDto {
    private UUID id;
    private String email;
    private PersonModel person;
    private String token;

    public AuthResponseDto(UUID id, String email, String token, PersonModel person) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.person = person;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PersonModel getPerson() {
        return person;
    }

    public void setPerson(PersonModel person) {
        this.person = person;
    }

}
