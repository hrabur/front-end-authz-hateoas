package pu.fmi.wordle.model.security;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = """
      select rp.PERMISSION from USERS u
      join USER_ROLE ur on ur.USER_ID = u.USER_ID
      join ROLE r on r.ROLE_ID = ur.ROLE_ID
      join ROLE_PERMISSION rp on rp.ROLE_ID = r.ROLE_ID
      where u.USER_ID = ?1
      """, nativeQuery = true)
  Collection<String> listPermissionsById(Long id);

  User getByEmail(String email);

}
