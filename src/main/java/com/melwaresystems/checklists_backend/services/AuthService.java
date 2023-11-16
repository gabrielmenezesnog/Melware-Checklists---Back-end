package com.melwaresystems.checklists_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.melwaresystems.checklists_backend.models.UserModel;
import com.melwaresystems.checklists_backend.repositories.UserRepository;
import com.melwaresystems.checklists_backend.services.exceptions.DuplicateEntryException;

import jakarta.transaction.Transactional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Transactional
    public UserModel registerUser(UserModel user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntryException();
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

}
