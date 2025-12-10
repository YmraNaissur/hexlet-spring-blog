package ru.naissur.dto.contact;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ContactDTO {

  private Long id;
  private String name;
  private String surname;
  private String phoneNumber;
  private LocalDate changedAt;
  private LocalDate createdAt;

}
