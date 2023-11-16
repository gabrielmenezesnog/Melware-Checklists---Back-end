package com.melwaresystems.checklists_backend.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.melwaresystems.checklists_backend.dto.UserDto;
import com.melwaresystems.checklists_backend.models.UserModel;
import com.melwaresystems.checklists_backend.repositories.UserRepository;
import com.melwaresystems.checklists_backend.services.exceptions.DatabaseException;
import com.melwaresystems.checklists_backend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    public UserModel findById(UUID id) {
        Optional<UserModel> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public UserDetails findByUsername(String email) {
        UserDetails user = userRepository.findByEmail(email);
        return user;
    }

    public Boolean existsByEmail(String email) {
        Boolean isEmailExists = userRepository.existsByEmail(email);
        return isEmailExists;
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
