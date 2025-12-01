package ru.naissur.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.naissur.model.Task;
import ru.naissur.repository.TasksRepository;

import java.util.List;
import java.util.Optional;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TasksController.class)
class TasksControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Faker faker;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TasksRepository tasksRepository;

  @MockBean
  private JpaMetamodelMappingContext jpaMetamodelMappingContext;

  @Test
  void testIndex() throws Exception {
    // arrange
    var task = buildTask(1L);
    when(tasksRepository.findAll()).thenReturn(List.of(task));

    // act
    var result = mockMvc.perform(get("/api/tasks"))
        .andExpect(status().isOk())
        .andReturn();
    var responseBody = result.getResponse().getContentAsString();

    // assert
    assertThatJson(responseBody).isArray();
    assertThatJson(responseBody).node("[0].id").isEqualTo(task.getId());
  }

  @Test
  void testShow() throws Exception {
    // arrange
    var task = buildTask(2L);
    when(tasksRepository.findById(2L)).thenReturn(Optional.of(task));

    // act
    var result = mockMvc.perform(get("/api/tasks/2"))
        .andExpect(status().isOk())
        .andReturn();
    var responseBody = result.getResponse().getContentAsString();

    // assert
    assertThatJson(responseBody).node("id").isEqualTo(task.getId());
    assertThatJson(responseBody).node("title").isEqualTo(task.getTitle());
  }

  @Test
  void testCreate() throws Exception {
    // arrange
    var taskToCreate = new Task();
    taskToCreate.setTitle("test task");

    var savedTask = new Task();
    savedTask.setId(4L);
    savedTask.setTitle("test task");

    when(tasksRepository.save(any(Task.class))).thenReturn(savedTask);
    ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);

    // act
    var result = mockMvc.perform(
            post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskToCreate)))
        .andExpect(status().isCreated())
        .andReturn();
    var responseBody = result.getResponse().getContentAsString();

    // assert
    verify(tasksRepository).save(taskArgumentCaptor.capture());
    assertThat(taskArgumentCaptor.getValue().getTitle()).isEqualTo("test task");
    assertThatJson(responseBody).node("id").isEqualTo(4L);
    assertThatJson(responseBody).node("title").isEqualTo("test task");
  }

  @Test
  void testUpdate() throws Exception {
    // arrange
    var existingTask = buildTask(5L);
    var taskToUpdate = new Task();
    taskToUpdate.setId(5L);
    taskToUpdate.setTitle("updated task");
    when(tasksRepository.findById(5L)).thenReturn(Optional.of(existingTask));
    when(tasksRepository.save(any(Task.class))).thenReturn(taskToUpdate);

    // act
    var result = mockMvc.perform(put("/api/tasks/5")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(taskToUpdate)))
        .andExpect(status().isOk())
        .andReturn();
    var responseBody = result.getResponse().getContentAsString();

    // assert
    verify(tasksRepository).save(any(Task.class));
    assertThatJson(responseBody).node("title").isEqualTo("updated task");
  }

  @Test
  void testDelete() throws Exception {
    // arrange
    doNothing().when(tasksRepository).deleteById(1L);

    // act
    mockMvc.perform(delete("/api/tasks/1"))
        .andExpect(status().isNoContent());

    // assert
    verify(tasksRepository).deleteById(1L);
  }

  private Task buildTask(Long id) {
    var task = new Task();
    task.setId(id);
    task.setTitle(faker.lorem().word());
    task.setDescription(faker.lorem().sentence(2));
    return task;
  }
}