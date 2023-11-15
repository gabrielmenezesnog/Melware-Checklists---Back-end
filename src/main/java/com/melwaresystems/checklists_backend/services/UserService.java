package com.melwaresystems.checklists_backend.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.melwaresystems.checklists_backend.dto.UserDto;
import com.melwaresystems.checklists_backend.models.UserModel;
import com.melwaresystems.checklists_backend.repositories.UserRepository;
import com.melwaresystems.checklists_backend.services.exceptions.DatabaseException;
import com.melwaresystems.checklists_backend.services.exceptions.DuplicateEntryException;
import com.melwaresystems.checklists_backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    public UserModel findByEmail(String email) {
        Optional<UserModel> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new ResourceNotFoundException(email));
    }

    public UserModel findById(UUID id) {
        Optional<UserModel> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Boolean existsByEmail(String email) {
        Boolean isEmailExists = userRepository.existsByEmail(email);
        return isEmailExists;
    }

    @Transactional
    public UserModel registerUser(UserModel user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntryException();
        }
        return user;
    }

    public UserModel updateUser(UserModel user) {
        try {
            UserModel entity = userRepository.getReferenceById(user.getIdUser());
            updateData(entity, user);
            return userRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(user.getIdUser());
        }
    }

    private void updateData(UserModel entity, UserModel user) {
        if (user.getEmail() != null)
            entity.setEmail(user.getEmail());
        if (user.getPassword() != null)
            entity.setPassword(user.getPassword());
        if (user.getPerson() != null)
            entity.setPerson(user.getPerson());
    }

    public void deleteById(UUID id) {
        try {
            userRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public UserModel fromDTO(UserDto userDto) {
        return new UserModel(userDto.getIdUser(), userDto.getEmail(), userDto.getPassword(), userDto.getUserStatus(),
                userDto.getDateCreated(), userDto.getPerson(), userDto.getTaskLists());
    }
}
