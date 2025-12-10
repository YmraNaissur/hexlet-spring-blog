package ru.naissur.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.naissur.dto.contact.ContactCreateDTO;
import ru.naissur.repository.ContactRepository;

import java.time.LocalDate;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContactsControllerTest {

  @Autowired
  private ContactRepository contactRepositoryMock;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testCreate() throws Exception {
    // arrange
    ContactCreateDTO createDTO = new ContactCreateDTO();
    createDTO.setName("John");
    createDTO.setSurname("Wick");
    createDTO.setPhoneNumber("+79999999999");

    // act
    var result = mockMvc.perform(post("/api/contacts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andReturn();
    var responseBody = result.getResponse().getContentAsString();

    // assert
    assertThatJson(responseBody).node("id").isEqualTo(1L);
    assertThatJson(responseBody).node("name").isEqualTo("John");
    assertThatJson(responseBody).node("surname").isEqualTo("Wick");
    assertThatJson(responseBody).node("phoneNumber").isEqualTo("\"+79999999999\"");
    assertThatJson(responseBody).node("createdAt").isEqualTo(LocalDate.now().toString());
    assertThatJson(responseBody).node("changedAt").isEqualTo(LocalDate.now().toString());
  }
}