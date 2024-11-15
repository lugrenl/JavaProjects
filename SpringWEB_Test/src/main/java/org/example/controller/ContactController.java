package org.example.controller;

import org.example.facade.ContactFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactFacade contactFacade;

    @Autowired
    public ContactController(ContactFacade contactFacade) {
        this.contactFacade = contactFacade;
    }

    @PostMapping
    public ContactDto addContact(@RequestParam("name") String name,
                                    @RequestParam("surname") String surname,
                                    @RequestParam("phoneNumber") String phoneNumber,
                                    @RequestParam("email") String email) {
        return contactFacade.addContact(name, surname, phoneNumber, email);
    }

    @GetMapping("/{contactId}")
    public ContactDto getContact(@PathVariable("contactId") long contactId) {
        return contactFacade.getContact(contactId);
    }

    @GetMapping
    public List<ContactDto> getAllContacts() {
        return contactFacade.getAllContacts();
    }

    @PutMapping("/{contactId}/{name}/{surname}/{phoneNumber}/{email}")
    public ContactDto updateContact(
            @PathVariable("contactId") long contactId,
            @PathVariable("name") String name,
            @PathVariable("surname") String surname,
            @PathVariable("phoneNumber") String phoneNumber,
            @PathVariable("email") String email) {
        return contactFacade.updateContact(contactId, name, surname, phoneNumber, email);
    }
}
