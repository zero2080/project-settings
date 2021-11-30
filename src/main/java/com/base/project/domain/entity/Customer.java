package com.base.project.domain.entity;


import com.base.project.domain.type.UserRole;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Customer {
  @Id
  @GeneratedValue
  private long id;
  private String name;
  private String email;
  private String password;
  private UserRole role;
}
