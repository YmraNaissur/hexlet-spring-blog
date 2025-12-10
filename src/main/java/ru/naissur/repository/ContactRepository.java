package ru.naissur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naissur.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
