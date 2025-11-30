package ru.naissur.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.naissur.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

  private UserMapper userMapper;

  @BeforeEach
  void setUp() {
    userMapper = new UserMapper();
  }

  @Test
  void testMapToDTO() {
    // arrange
    User user = new User();
    user.setId(1L);
    user.setFirstName("John");
    user.setLastName("Doe");

    // act
    var userDTO = userMapper.toDTO(user);

    // assert
    assertEquals(1L, userDTO.getId());
    assertEquals("John", userDTO.getFirstName());
    assertEquals("Doe", userDTO.getLastName());
    assertEquals("John Doe", userDTO.getUserName());
  }
}