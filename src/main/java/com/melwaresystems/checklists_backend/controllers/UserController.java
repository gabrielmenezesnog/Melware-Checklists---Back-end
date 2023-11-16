package com.melwaresystems.checklists_backend.controllers;

import java.util.stream.Collectors;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melwaresystems.checklists_backend.dto.UserDto;
import com.melwaresystems.checklists_backend.models.UserModel;
import com.melwaresystems.checklists_backend.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public List<UserDto> getUsers() {
        List<UserModel> list = userService.getUsers();
        List<UserDto> listDto = list.stream().map(item -> new UserDto(item)).collect(Collectors.toList());

        return listDto;
    }

    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable UUID id) {
        UserModel user = userService.findById(id);

        return ResponseEntity.ok().body(new UserDto(user));
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
