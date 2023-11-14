package com.melwaresystems.checklists_backend.services.exceptions;

public class DuplicateEntryException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    public DuplicateEntryException() {
        super("this email is already registered");
    }

    public static Long getSerialversionuid() {
        return serialVersionUID;
    }
}
