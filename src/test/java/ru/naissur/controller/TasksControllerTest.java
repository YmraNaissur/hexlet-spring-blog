package ru.naissur.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.naissur.model.Task;
import ru.naissur.repository.TasksRepository;

import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TasksController.class)
class TasksControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TasksRepository tasksRepository;

  @MockBean
  private JpaMetamodelMappingContext jpaMetamodelMappingContext;

  @Test
  void testIndex() throws Exception{
    // arrange
    Task task = new Task();
    task.setId(1L);
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
}