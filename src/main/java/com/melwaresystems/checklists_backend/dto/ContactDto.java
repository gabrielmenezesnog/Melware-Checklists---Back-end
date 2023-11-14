package com.melwaresystems.checklists_backend.dto;

import java.io.Serializable;
import java.util.UUID;

import com.melwaresystems.checklists_backend.models.ContactModel;

import jakarta.validation.constraints.NotBlank;

public class ContactDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private UUID idContact;

    @NotBlank
    private String phoneNumber;

    public ContactDto() {
    }

    public ContactDto(ContactModel contact) {
        idContact = contact.getIdContact();
        phoneNumber = contact.getPhoneNumber();
    }

    public UUID getIdContact() {
        return idContact;
    }

    public void setIdContact(UUID idContact) {
        this.idContact = idContact;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
