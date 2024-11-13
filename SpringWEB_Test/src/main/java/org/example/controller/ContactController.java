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
    public ContactDto addContact(@RequestParam String name,
                                    @RequestParam String surname,
                                    @RequestParam String phoneNumber,
                                    @RequestParam String email) {
        return contactFacade.addContact(name, surname, phoneNumber, email);
    }

    @GetMapping("/{contactId}")
    public ContactDto getContact(@PathVariable long contactId) {
        return contactFacade.getContact(contactId);
    }

    @GetMapping
    public List<ContactDto> getAllContact() {
        return contactFacade.getAllContactS();
    }

    @PutMapping("/{contactId}/{name}/{surname}/{phoneNumber}/{email}")
    public ContactDto updateContact(
            @PathVariable long contactId,
            @PathVariable String name,
            @PathVariable String surname,
            @PathVariable String phoneNumber,
            @PathVariable String email) {
        return contactFacade.updateContact(contactId, name, surname, phoneNumber, email);
    }
}
