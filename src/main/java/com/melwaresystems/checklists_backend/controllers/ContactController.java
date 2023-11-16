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

import com.melwaresystems.checklists_backend.dto.ContactDto;
import com.melwaresystems.checklists_backend.models.ContactModel;
import com.melwaresystems.checklists_backend.services.ContactService;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/getAll")
    public List<ContactDto> getContacts() {
        List<ContactModel> list = contactService.getContacts();
        List<ContactDto> listDto = list.stream().map(item -> new ContactDto(item)).collect(Collectors.toList());
        return listDto;
    }

    @GetMapping("/search-by-id/{id}")
    public ResponseEntity<ContactDto> findById(@PathVariable UUID id) {
        ContactModel contact = contactService.findById(id);

        return ResponseEntity.ok().body(new ContactDto(contact));
    }

    @PostMapping("/create-contact")
    public ResponseEntity<String> createContact(@RequestBody ContactDto contactDto) {

        ContactModel contact = contactService.fromDTO(contactDto);

        contact = contactService.createContact(contact);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(contact.getIdContact())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
