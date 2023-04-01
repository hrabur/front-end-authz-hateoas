package pu.fmi.wordle.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import pu.fmi.wordle.api.Error;
import pu.fmi.wordle.logic.GameService;

@Controller
public class HomeController {

  final GameService gameService;

  public HomeController(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping({"/", "/login", "/games", "/games/{gameId}", "/config"})
  public String welcome() {
    return "/index.html";
  }

  @ResponseBody
  @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> login(@RequestBody UsernamePasswordToken token) {
    try {
      SecurityUtils.getSubject().login(token);
      return ResponseEntity.ok().build();
    } catch (AuthenticationException e) {
      return ResponseEntity.badRequest().body(new Error("wrong-credentials", null, e.getMessage()));
    }
  }

  @ResponseBody
  @GetMapping("/logout")
  public ResponseEntity<Void> logout() {
    SecurityUtils.getSubject().logout();
    return ResponseEntity.ok().build();
  }
}
