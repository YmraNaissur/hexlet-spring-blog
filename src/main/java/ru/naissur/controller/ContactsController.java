package ru.naissur.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.naissur.dto.contact.ContactCreateDTO;
import ru.naissur.dto.contact.ContactDTO;
import ru.naissur.model.Contact;
import ru.naissur.repository.ContactRepository;

@RestController
@RequestMapping("/api/contacts")
public class ContactsController {

  @Autowired
  private ContactRepository contactRepository;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ContactDTO create(@Valid @RequestBody ContactCreateDTO contactCreateDTO) {
    Contact entity = new Contact();
    entity.setName(contactCreateDTO.getName());
    entity.setSurname(contactCreateDTO.getSurname());
    entity.setPhoneNumber(contactCreateDTO.getPhoneNumber());

    entity = contactRepository.save(entity);

    ContactDTO response = new ContactDTO();
    response.setId(entity.getId());
    response.setName(entity.getName());
    response.setSurname(entity.getSurname());
    response.setPhoneNumber(entity.getPhoneNumber());
    response.setCreatedAt(entity.getCreatedAt());
    response.setChangedAt(entity.getChangedAt());
    return response;
  }

}
