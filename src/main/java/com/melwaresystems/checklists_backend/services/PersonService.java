package com.melwaresystems.checklists_backend.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.melwaresystems.checklists_backend.dto.PersonDto;
import com.melwaresystems.checklists_backend.models.PersonModel;
import com.melwaresystems.checklists_backend.repositories.PersonRepository;
import com.melwaresystems.checklists_backend.services.exceptions.DatabaseException;
import com.melwaresystems.checklists_backend.services.exceptions.DuplicateEntryException;
import com.melwaresystems.checklists_backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<PersonModel> getPersons() {
        return personRepository.findAll();
    }

    public PersonModel findById(UUID id) {
        Optional<PersonModel> user = personRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public PersonModel createPerson(PersonModel person) {
        try {
            personRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntryException();
        }
        return person;
    }

    public PersonModel updatePerson(PersonModel person) {
        try {
            PersonModel entity = personRepository.getReferenceById(person.getIdPerson());
            updateData(entity, person);
            return personRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(person.getIdPerson());
        }
    }

    private void updateData(PersonModel entity, PersonModel user) {
        if (user.getName() != null)
            entity.setName(user.getName());
        if (user.getLastName() != null)
            entity.setLastName(user.getLastName());
        if (user.getCpf() != null)
            entity.setCpf(user.getCpf());
        if (user.getBirthDate() != null)
            entity.setBirthDate(user.getBirthDate());
        if (user.getContact() != null)
            entity.setContact(user.getContact());
    }

    public void deleteById(UUID id) {
        try {
            personRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public PersonModel fromDTO(PersonDto personDto) {
        return new PersonModel(personDto.getIdPerson(), personDto.getName(), personDto.getLastName(),
                personDto.getCpf(),
                personDto.getBirthDate(), personDto.getContact());
    }
}
