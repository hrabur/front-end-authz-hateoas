package pu.fmi.wordle.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.apache.shiro.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import pu.fmi.wordle.model.security.Permissions;
import pu.fmi.wordle.model.security.User;
import pu.fmi.wordle.web.HomeController;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

  private static final TypeMap<User, UserModel> USER_TO_USER_MODEL =
      new ModelMapper().createTypeMap(User.class, UserModel.class);

  public UserModelAssembler() {
    super(UserApi.class, UserModel.class);
  }

  @Override
  public UserModel toModel(User user) {
    var userModel = USER_TO_USER_MODEL.map(user);
    // Anonymous access links
    userModel.add(linkTo(methodOn(GameApi.class).startNewGame()).withRel("startNewGame"),
        linkTo(methodOn(GameApi.class).listLast10("top10")).withRel("top10"),
        linkTo(methodOn(UserApi.class).showUser(user.getId())).withSelfRel());

    if (SecurityUtils.getSubject().isAuthenticated()) {
      userModel.add(linkTo(methodOn(HomeController.class).logout()).withRel("logout"));
    } else {
      userModel.add(linkTo(methodOn(HomeController.class).login(null)).withRel("login"));
    }

    if (SecurityUtils.getSubject().isPermitted(Permissions.GAME_QUERY_MY_TOP10)) {
      userModel.add(linkTo(methodOn(GameApi.class).listLast10("myTop10")).withRel("myTop10"));
    }

    if (SecurityUtils.getSubject().isPermitted(Permissions.CONFIG_READ)) {
      userModel.add(linkTo(methodOn(ConfigApi.class).getConfig()).withRel("readConfig"));
    }

    return userModel;
  }

  public UserModel toAnonymousUserModel() {
    User user = new User();
    user.setName("Anonymous");
    return toModel(user);
  }
}
