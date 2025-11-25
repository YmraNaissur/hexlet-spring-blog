package ru.naissur.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.naissur.exception.ResourceNotFoundException;
import ru.naissur.model.Task;
import ru.naissur.repository.TasksRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TasksController {

  private final TasksRepository tasksRepository;

  @GetMapping(path = "")
  public List<Task> index() {
    return tasksRepository.findAll();
  }

  @GetMapping(path = "/{id}")
  public Task show(@PathVariable Long id) {
    return tasksRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
  }

  @PostMapping(path = "")
  @ResponseStatus(HttpStatus.CREATED)
  public Task create(@RequestBody Task task) {
    return tasksRepository.save(task);
  }

  @PutMapping(path = "/{id}")
  public Task update(@PathVariable Long id, @RequestBody Task task) {
    Task existingTask = tasksRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

    existingTask.setTitle(task.getTitle());
    existingTask.setDescription(task.getDescription());
    return tasksRepository.save(existingTask);
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    tasksRepository.deleteById(id);
  }

}
