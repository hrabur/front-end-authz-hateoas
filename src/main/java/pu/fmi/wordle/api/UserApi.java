package pu.fmi.wordle.api;

import org.apache.shiro.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pu.fmi.wordle.logic.UserService;

@RestController
@RequestMapping("/api/users")
public class UserApi {

  final UserService userService;
  final UserModelAssembler userModelAssembler;


  public UserApi(UserService userService, UserModelAssembler userModelAssembler) {
    this.userService = userService;
    this.userModelAssembler = userModelAssembler;
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserModel> showUser(Long id) {
    var user = userService.getUserById(id);
    if (user == null)
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok(userModelAssembler.toModel(user));
  }

  @GetMapping("/current")
  public UserModel getCurrentUser() {
    var username = (String) SecurityUtils.getSubject().getPrincipal();
    if (username == null)
      return userModelAssembler.toAnonymousUserModel();

    var user = userService.getUserByUsername(username);
    return userModelAssembler.toModel(user);
  }
}
