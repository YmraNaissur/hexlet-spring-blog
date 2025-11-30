package ru.naissur.mapper;

import org.springframework.stereotype.Component;
import ru.naissur.dto.UserDTO;
import ru.naissur.model.User;

@Component
public class UserMapper {

  public UserDTO toDTO(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setFirstName(user.getFirstName());
    userDTO.setLastName(user.getLastName());
    userDTO.setUserName(user.getFirstName() + " " + user.getLastName());
    return userDTO;
  }

}
