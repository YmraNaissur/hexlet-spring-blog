package ru.naissur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naissur.model.Task;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

}
