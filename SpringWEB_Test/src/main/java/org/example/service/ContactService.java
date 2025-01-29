package org.example.service;

import org.example.dao.ContactDao;
import org.example.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class ContactService {

    private final ContactDao contactDao;
    private final ContactReader contactReader;

    @Autowired
    public ContactService(ContactDao contactDao, ContactReader contactReader) {
        this.contactDao = contactDao;
        this.contactReader = contactReader;
    }

    public void saveContacts(Path filePath) {
        var contacts = contactReader.readFromFile(filePath);
        contactDao.saveAll(contacts);
    }

    public List<Contact> getContacts() {
        return contactDao.getAllContacts();
    }
}