package org.example.dao;

import org.example.exceptions.ContactNotFoundException;
import org.example.model.Contact;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Primary
@Repository
public class HibernateContactDao implements ContactDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateContactDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Contact addContactReturnContact(String name, String surname, String email, String phoneNumber) {
        Contact contact = new Contact(name, surname, email, phoneNumber);
        addContact(contact);
        return contact;
    }

    @Override
    public long addContact(Contact contact) {
        try(var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            long contactId = (Long) session.save(contact);
            transaction.commit();
            return contactId;
        }
    }

    @Override
    public Contact getContact(long contactId) {
        try(var session = sessionFactory.openSession()) {
            return session.get(Contact.class, contactId);
        }
    }

    @Override
    public Contact updateContact(long contactId, String name, String surname, String email, String phoneNumber) {
        try(var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var contact = session.get(Contact.class, contactId);
            if (contact != null) {
                contact.setName(name);
                contact.setSurname(surname);
                contact.setEmail(email);
                contact.setPhoneNumber(phoneNumber);
            } else {
                throw new ContactNotFoundException("Contact not found: " + contactId);
            }
            transaction.commit();
            return contact;
        }
    }

    @Override
    public List<Contact> getAllContacts() {
        try(var session = sessionFactory.openSession()) {
            return session.createQuery("from Contact", Contact.class).getResultList();
        }
    }

    @Override
    public void updatePhoneNumber(long contactId, String phoneNumber) {
        try(var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var contact = session.get(Contact.class, contactId);
            if (contact != null) {
                contact.setPhoneNumber(phoneNumber);
            } else {
                throw new ContactNotFoundException("Contact not found: " + contactId);
            }
            transaction.commit();
        }
    }

    @Override
    public void updateEmail(long contactId, String email) {
        try(var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var contact = session.get(Contact.class, contactId);
            if (contact != null) {
                contact.setEmail(email);
            } else {
                throw new ContactNotFoundException("Contact not found: " + contactId);
            }
            transaction.commit();
        }
    }

    @Override
    public void deleteContact(long contactId) {
        try(var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var contact = session.get(Contact.class, contactId);
            if (contact != null) {
                session.remove(contact);
            } else {
                throw new ContactNotFoundException("Contact not found: " + contactId);
            }
            transaction.commit();
        }
    }

    @Override
    public void saveAll(Collection<Contact> contacts) {
        try(var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            contacts.forEach(session::save);
            transaction.commit();
        }
    }
}
