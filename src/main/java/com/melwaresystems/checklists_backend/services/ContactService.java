package com.melwaresystems.checklists_backend.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.melwaresystems.checklists_backend.dto.ContactDto;
import com.melwaresystems.checklists_backend.models.ContactModel;
import com.melwaresystems.checklists_backend.repositories.ContactRepository;
import com.melwaresystems.checklists_backend.services.exceptions.DatabaseException;
import com.melwaresystems.checklists_backend.services.exceptions.DuplicateEntryException;
import com.melwaresystems.checklists_backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public List<ContactModel> getContacts() {
        return contactRepository.findAll();
    }

    public ContactModel findById(UUID id) {
        Optional<ContactModel> contact = contactRepository.findById(id);
        return contact.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public ContactModel createContact(ContactModel contact) {
        try {
            contactRepository.save(contact);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntryException();
        }
        return contact;
    }

    public ContactModel updateContact(ContactModel contact) {
        try {
            ContactModel entity = contactRepository.getReferenceById(contact.getIdContact());
            updateData(entity, contact);
            return contactRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(contact.getIdContact());
        }
    }

    private void updateData(ContactModel entity, ContactModel contact) {
        if (contact.getPhoneNumber() != null)
            entity.setPhoneNumber(contact.getPhoneNumber());
    }

    public void deleteById(UUID id) {
        try {
            contactRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ContactModel fromDTO(ContactDto contactDto) {
        return new ContactModel(contactDto.getIdContact(), contactDto.getPhoneNumber());
    }
}
