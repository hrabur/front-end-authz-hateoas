package pu.fmi.wordle.api;

import java.util.Collection;
import org.springframework.hateoas.RepresentationModel;

public class UserModel extends RepresentationModel<UserModel> {
  Long id;
  String name;
  Collection<String> permissions;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Collection<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(Collection<String> permissions) {
    this.permissions = permissions;
  }
}
