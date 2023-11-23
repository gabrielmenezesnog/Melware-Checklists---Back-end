package com.melwaresystems.checklists_backend.controllers;

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

import com.melwaresystems.checklists_backend.dto.AuthDto;
import com.melwaresystems.checklists_backend.dto.AuthResponseDto;
import com.melwaresystems.checklists_backend.dto.UserDto;
import com.melwaresystems.checklists_backend.models.UserModel;
import com.melwaresystems.checklists_backend.models.enums.Status;
import com.melwaresystems.checklists_backend.models.enums.UserRole;
import com.melwaresystems.checklists_backend.services.AuthService;
import com.melwaresystems.checklists_backend.services.ContactService;
import com.melwaresystems.checklists_backend.services.TokenService;
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

    @Autowired
    TokenService tokenService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthDto authDto) {

        boolean isEmailExists = userService.existsByEmail(authDto.getEmail());

        if (!isEmailExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDto.getEmail(),
                            authDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserModel user = (UserModel) authentication.getPrincipal();
            String token = tokenService.generateToken(user);

            return new ResponseEntity<>(
                    new AuthResponseDto(user.getIdUser(), user.getEmail(), token, user.getPerson()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> createuser(@RequestBody UserDto userDto) {
        boolean isEmailExists = userService.existsByEmail(userDto.getEmail());

        UserModel user = userService.fromDTO(userDto);

        if (isEmailExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setDateCreated(LocalDateTime.now((ZoneId.of("UTC"))));
        user.setStatus(Status.ACTIVE);
        user.setRole(UserRole.USER);

        user = authService.registerUser(user);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            userDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            var token = tokenService.generateToken((UserModel) authentication.getPrincipal());

            return new ResponseEntity<>(
                    new AuthResponseDto(user.getIdUser(), user.getEmail(), token, user.getPerson()),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
