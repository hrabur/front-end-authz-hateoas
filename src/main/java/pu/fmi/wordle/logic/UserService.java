package pu.fmi.wordle.logic;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pu.fmi.wordle.model.security.User;
import pu.fmi.wordle.model.security.UserRepository;

@Service
@Transactional
public class UserService {

  @Autowired
  UserRepository userRepo;

  public Collection<String> listUserPermissionsById(Long userId) {
    return userRepo.listPermissionsById(userId);
  }

  public User getUserByUsername(String username) {
    return userRepo.getByEmail(username);
  }

  public User getUserById(Long id) {
    return userRepo.findById(id).orElse(null);
  }

}
