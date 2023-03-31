package pu.fmi.wordle.model.security;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Role {

  @Id
  @Column(name = "role_id")
  @GeneratedValue
  Long id;

  String name;

  @OneToMany
  @JoinColumn(name = "role_id")
  Collection<Permission> permissions;

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

  public Collection<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(Collection<Permission> permissions) {
    this.permissions = permissions;
  }
}
