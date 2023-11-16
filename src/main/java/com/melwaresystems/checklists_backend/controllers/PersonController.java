package com.melwaresystems.checklists_backend.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.melwaresystems.checklists_backend.dto.PersonDto;
import com.melwaresystems.checklists_backend.models.PersonModel;
import com.melwaresystems.checklists_backend.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/getAll")
    public List<PersonDto> getUsers() {
        List<PersonModel> list = personService.getPersons();
        List<PersonDto> listDto = list.stream().map(item -> new PersonDto(item)).collect(Collectors.toList());
        return listDto;
    }

    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable UUID id) {
        PersonModel person = personService.findById(id);

        return ResponseEntity.ok().body(new PersonDto(person));
    }

    @PostMapping("/create-person")
    public ResponseEntity<String> createPerson(@RequestBody PersonDto personDto) {

        PersonModel person = personService.fromDTO(personDto);

        person.setName(person.getName().toLowerCase());
        person.setLastName(person.getLastName().toLowerCase());

        person = personService.createPerson(person);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(person.getIdPerson())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
