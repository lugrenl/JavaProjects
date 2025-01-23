package org.example.dao;

import org.example.model.Contact;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ContactDaoImplementation implements ContactDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public ContactDaoImplementation(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public List<Contact> getAllContacts() {
        // TODO Implement me!
        return Collections.emptyList();
    }

    @Override
    public Contact addContact(String name, String surname, String phoneNumber, String email) {
        return null;
    }

    @Override
    public Optional<Contact> findContact(long contactId) {
        return Optional.empty();
    }

    public Contact getContact(long contactId) {
        // TODO Implement me!
        return null;
    }

    @Override
    public Contact updateContact(long contactId, String name, String surname, String phoneNumber, String email) {
        return null;
    }

    public long addContact(Contact contact) {
        // TODO Implement me!
        return -1;
    }

    public void updatePhoneNumber(long contactId, String phoneNumber) {
        // TODO Implement me!
    }

    public void updateEmail(long contactId, String email) {
        // TODO Implement me!
    }

    public void deleteContact(long contactId) {
        // TODO Implement me!
    }
}