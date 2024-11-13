package org.example.dao;

import org.example.model.Contact;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryContactDao implements ContactDao {
    private long contactId = 1L;
    private final Map<Long, Contact> contactIdMap;

    public InMemoryContactDao() {
        this.contactIdMap = new HashMap<>();
    }

    @Override
    public Contact addContact(String name, String surname, String phoneNumber, String email) {
        Contact contact = new Contact(contactId, name, surname, phoneNumber, email);
        contactIdMap.put(contactId++, contact);
        return contact;
    }

    @Override
    public Optional<Contact> findContact(long contactId) {
        return Optional.ofNullable(contactIdMap.get(contactId));
    }

    @Override
    public Contact getContact(long contactId) {
        return findContact(contactId).orElseThrow(() -> new IllegalArgumentException("Contact not found" + contactId));
    }

    @Override
    public Contact updateContact(long contactId, String name, String surname, String phoneNumber, String email) {
        Contact contact = getContact(contactId);
        contact.setName(name);
        contact.setSurname(surname);
        contact.setPhoneNumber(phoneNumber);
        contact.setEmail(email);
        return contact;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactIdMap.values().stream().toList();
    }
}
