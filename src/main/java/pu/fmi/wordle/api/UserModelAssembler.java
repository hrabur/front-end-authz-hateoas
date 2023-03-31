package pu.fmi.wordle.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import pu.fmi.wordle.logic.UserService;
import pu.fmi.wordle.model.security.User;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

  private static final TypeMap<User, UserModel> USER_TO_USER_MODEL =
      new ModelMapper().createTypeMap(User.class, UserModel.class);

  final UserService userService;

  public UserModelAssembler(UserService userService) {
    super(UserApi.class, UserModel.class);
    this.userService = userService;
  }

  @Override
  public UserModel toModel(User user) {
    var userModel = USER_TO_USER_MODEL.map(user);
    var permissions = userService.listUserPermissionsById(user.getId());
    userModel.setPermissions(permissions);
    return userModel.add(linkTo(methodOn(GameApi.class).startNewGame()).withRel("startNewGame"),
        linkTo(methodOn(GameApi.class).listLast10("myTop10")).withRel("myTop10"),
        linkTo(methodOn(UserApi.class).showUser(user.getId())).withSelfRel());
  }
}
