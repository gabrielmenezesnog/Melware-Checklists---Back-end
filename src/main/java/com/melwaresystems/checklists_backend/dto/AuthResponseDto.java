package com.melwaresystems.checklists_backend.dto;

import java.util.UUID;

public class AuthResponseDto {
    private UUID id;
    private String email;

    public AuthResponseDto(UUID id, String email) {
        this.id = id;
        this.email = email;
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

}
