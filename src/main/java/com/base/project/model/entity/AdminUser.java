package com.base.project.model.entity;

import com.base.project.model.type.CommonRole;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {
  @Id
  @GeneratedValue
  @Type(type="uuid-char")
  private UUID id;
  private String username;
  private String password;
  private CommonRole role;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdminUser user = (AdminUser) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString(){
    return String.format("AdminUser - {id : %s, username : %s, password : %s, role : %s}",id.toString(),username,password,role.name());
  }
}
