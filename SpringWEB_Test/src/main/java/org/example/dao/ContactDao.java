package org.example.dao;

import org.example.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactDao {
    Contact addContact(String name, String surname, String phoneNumber, String email);
    long addContact(Contact contact);
    Optional <Contact> findContact(long contactId);
    Contact getContact(long contactId);
    Contact updateContact(long contactId, String name, String surname, String phoneNumber, String email);
    List<Contact> getAllContacts();
    void updatePhoneNumber(long contactId, String phoneNumber);
    void updateEmail(long contactId, String email);
    void deleteContact(long contactId);
}
