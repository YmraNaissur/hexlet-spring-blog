package ru.naissur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naissur.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
