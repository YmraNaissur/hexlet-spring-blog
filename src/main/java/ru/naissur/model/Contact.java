package ru.naissur.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "contacts")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String surname;
  private String phoneNumber;

  @LastModifiedDate
  private LocalDate changedAt;

  @CreatedDate
  private LocalDate createdAt;

}
