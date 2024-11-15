package org.example.facade;

import org.example.controller.ContactDto;
import org.example.dao.ContactDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContactFacade {
    private final ContactDao contactDao;

    @Autowired
    public ContactFacade(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public ContactDto addContact(String name, String surname, String phoneNumber, String email) {
        return new ContactDto(contactDao.addContact(name, surname, phoneNumber, email));
    }

    public ContactDto getContact(long contactId) {
        return new ContactDto(contactDao.getContact(contactId));
    }

    public List<ContactDto> getAllContacts() {
        return contactDao.getAllContacts().stream().map(ContactDto::new).toList();
    }

    public ContactDto updateContact(long contactId, String name, String surname, String phoneNumber, String email) {
        return new ContactDto(contactDao.updateContact(contactId, name, surname, phoneNumber, email));
    }
}
