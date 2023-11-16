package com.melwaresystems.checklists_backend.controllers;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.melwaresystems.checklists_backend.dto.AuthDto;
import com.melwaresystems.checklists_backend.dto.AuthResponseDto;
import com.melwaresystems.checklists_backend.dto.UserDto;
import com.melwaresystems.checklists_backend.models.UserModel;
import com.melwaresystems.checklists_backend.models.enums.Status;
import com.melwaresystems.checklists_backend.models.enums.UserRole;
import com.melwaresystems.checklists_backend.services.AuthService;
import com.melwaresystems.checklists_backend.services.ContactService;
import com.melwaresystems.checklists_backend.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    ContactService contactService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthDto authDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDto.getEmail(),
                        authDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserModel user = (UserModel) userService.findByUsername(authDto.getEmail());

        return new ResponseEntity<>(new AuthResponseDto(user.getIdUser(), user.getEmail()),
                HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserModel> createuser(@RequestBody UserDto userDto) {
        boolean isEmailExists = userService.existsByEmail(userDto.getEmail());
        boolean isPhoneNumberExists = contactService
                .existsByPhoneNumber(userDto.getPerson().getContact().getPhoneNumber());

        if (isEmailExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        if (isPhoneNumberExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        UserModel user = userService.fromDTO(userDto);

        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setDateCreated(LocalDateTime.now((ZoneId.of("UTC"))));
        user.setStatus(Status.ACTIVE);
        user.setRole(UserRole.USER);

        user = authService.registerUser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getIdUser())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }

}
