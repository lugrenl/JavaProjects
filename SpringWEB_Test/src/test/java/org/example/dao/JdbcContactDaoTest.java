package org.example.dao;

import org.example.config.ContactsManagerConfig;
import org.example.model.Contact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

/**
 * Unit tests for {@link JdbcContactDao}.
 * <p>
 * Аннотация @Sql подтягивает SQL-скрипт contact.sql, который будет применен к базе перед выполнением теста.
 * Contact.sql создает таблицу CONTACT с полями (ID, NAME, SURNAME, EMAIL, PHONE_NUMBER) и вставляет в нее 2 записи.
 * <p>
 * Тесты проверяют корректность реализации ContactDao.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ContactsManagerConfig.class)
@Sql("classpath:contact.sql")
public record JdbcContactDaoTest(@Autowired ContactDao contactDao) {

    private static final Contact IVAN = new Contact(1000L, "Ivan", "Ivanov", "iivanov@gmail.com", "1234567");
    private static final Contact MARIA = new Contact(2000L, "Maria", "Ivanova", "mivanova@gmail.com", "7654321");

    /**
     * There are two contacts inserted in the database in contact.sql.
     */
    private static final List<Contact> PERSISTED_CONTACTS = List.of(IVAN, MARIA);

    @Test
    void addContact() {
        var contact = new Contact("Jackie", "Chan", "jchan@gmail.com", "1234567890");
        var contactId = contactDao.addContact(contact);
        contact.setId(contactId);

        var contactInDb = contactDao.getContact(contactId);

        assertThat(contactInDb).isEqualTo(contact);
    }

    @Test
    void getContact() {
        var contact = contactDao.getContact(IVAN.getId());

        assertThat(contact).isEqualTo(IVAN);
    }

    @Test
    void getAllContacts() {
        var contacts = contactDao.getAllContacts();

        assertThat(contacts).containsAll(PERSISTED_CONTACTS);
    }

    @Test
    void updatePhoneNumber() {
        var contact = new Contact("Jekyll", "Hide", "jhide@gmail.com", "");
        var contactId = contactDao.addContact(contact);

        var newPhone = "777-77-77";
        contactDao.updatePhoneNumber(contactId, newPhone);

        var updatedContact = contactDao.getContact(contactId);

        assertThat(updatedContact.getPhoneNumber()).isEqualTo(newPhone);
    }

    @Test
    void updateEmail() {
        var contact = new Contact("Captain", "America", "", "");
        var contactId = contactDao.addContact(contact);

        var newEmail = "cap@gmail.com";
        contactDao.updateEmail(contactId, newEmail);

        var updatedContact = contactDao.getContact(contactId);

        assertThat(updatedContact.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void deleteContact() {
        var contact = new Contact("To be", "Deleted", "", "");
        var contactId = contactDao.addContact(contact);

        contactDao.deleteContact(contactId);

        assertThatThrownBy(() -> contactDao.getContact(contactId))
                .isInstanceOf(EmptyResultDataAccessException.class);

    }

    @Test
    void updateContact() {
        var contact = new Contact("Name", "Surname", "email", "phonenumber");
        var contactId = contactDao.addContact(contact);

        var newName = "NewName";
        var newSurname = "NewSurname";
        var newEmail = "newemail";
        var newPhoneNumber = "newphonenumber";

        var updatedContact = contactDao.updateContact(contactId, newName, newSurname, newEmail, newPhoneNumber);

        assertThat(updatedContact).isEqualTo(new Contact(contactId, newName, newSurname, newEmail, newPhoneNumber));
    }
}
