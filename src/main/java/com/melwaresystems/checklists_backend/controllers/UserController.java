package com.melwaresystems.checklists_backend.controllers;

import java.util.stream.Collectors;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.melwaresystems.checklists_backend.dto.UserDto;
import com.melwaresystems.checklists_backend.models.UserModel;
import com.melwaresystems.checklists_backend.models.enums.Status;
import com.melwaresystems.checklists_backend.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public List<UserDto> getUsers() {
        List<UserModel> list = userService.getUsers();
        List<UserDto> listDto = list.stream().map(item -> new UserDto(item)).collect(Collectors.toList());
        return listDto;
    }

    @GetMapping("/search-by-email/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email) {
        UserModel user = userService.findByEmail(email);

        return ResponseEntity.ok().body(new UserDto(user));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createuser(@RequestBody UserDto userDto) {
        boolean isEmailExists = userService.existsByEmail(userDto.getEmail());

        if (isEmailExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
        }

        UserModel user = userService.fromDTO(userDto);

        user.setEmail(user.getEmail().toLowerCase());
        user.setDateCreated(LocalDateTime.now((ZoneId.of("UTC"))));
        user.setUserStatus(Status.ACTIVE);

        user = userService.registerUser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getIdUser())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto, @PathVariable UUID id) {
        UserModel user = userService.fromDTO(userDto);
        user.setIdUser(id);
        user = userService.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
