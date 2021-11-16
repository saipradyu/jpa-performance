package com.github.saipradyu.performance.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {
  @Id
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
}
