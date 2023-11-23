package com.melwaresystems.checklists_backend.models;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTACT")
public class ContactModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private UUID idContact;

    @Column(nullable = false, length = 60)
    private String phoneNumber;

    public ContactModel() {
    }

    public ContactModel(UUID idContact, String phoneNumber) {
        this.idContact = idContact;
        this.phoneNumber = phoneNumber;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idContact == null) ? 0 : idContact.hashCode());
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
        ContactModel other = (ContactModel) obj;
        if (idContact == null) {
            if (other.idContact != null)
                return false;
        } else if (!idContact.equals(other.idContact))
            return false;
        return true;
    }

}
