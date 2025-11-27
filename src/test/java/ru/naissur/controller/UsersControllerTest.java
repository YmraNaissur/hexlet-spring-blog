package ru.naissur.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.naissur.model.User;
import ru.naissur.repository.UserRepository;

import java.util.HashMap;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Faker faker;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    userRepository.deleteAll();
  }

  @Test
  public void testGetUser() throws Exception {
    var user = Instancio.of(User.class)
        .ignore(Select.field(User::getId))
        .supply(Select.field(User::getEmail), () -> "email@example.com")
        .create();
    userRepository.save(user);

    var request = get("/api/users/" + user.getId());

    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.email").value("email@example.com"));
  }

  @Test
  public void testGetAllUsers() throws Exception {
    var result = mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andReturn();

    var body = result.getResponse().getContentAsString();
    assertThatJson(body).isArray();
  }

  @Test
  public void testUpdateUser() throws Exception {
    var user = Instancio.of(User.class)
        .ignore(Select.field(User::getId))
        .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
        .create();
    userRepository.save(user);

    var data = new HashMap<>();
    data.put("firstName", "Mike");
    data.put("lastName", "Tyson");

    MockHttpServletRequestBuilder request = put("/api/users/" + user.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(data));

    mockMvc.perform(request)
        .andExpect(status().isOk());

    user = userRepository.findById(user.getId()).get();
    assertEquals("Mike", user.getFirstName());
    assertEquals("Tyson", user.getLastName());
  }

  @Test
  public void testCreateUser_201_andBody() throws Exception {
    var user = new User();
    user.setEmail("email@example.com");
    user.setFirstName("John");
    user.setLastName("Doe");

    MockHttpServletRequestBuilder request = post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user));

    mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.email").value("email@example.com"));
  }

  @Test
  public void testDeleteUser() throws Exception {
    var user = Instancio.of(User.class)
        .ignore(Select.field(User::getId))
        .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
        .create();

    userRepository.save(user);

    // first get will return User
    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty());

    mockMvc.perform(delete("/api/users/" + user.getId()))
        .andExpect(status().isNoContent());

    // after deletion there are no Users
    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }
}