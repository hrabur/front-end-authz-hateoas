package pu.fmi.wordle.api;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pu.fmi.wordle.logic.GameService;
import pu.fmi.wordle.logic.UnknownWordException;

@RestController
@RequestMapping("/api/games")
public class GameApi {

  final GameService gameService;
  final GameModelAssembler gameModelAssembler;

  public GameApi(GameService gameService, GameModelAssembler gameModelAssembler) {
    this.gameService = gameService;
    this.gameModelAssembler = gameModelAssembler;
  }

  @GetMapping()
  public CollectionModel<GameModel> listLast10(@RequestParam String filter) {
    return switch (filter) {
      case "myTop10" -> {
        SecurityUtils.getSubject().checkPermission("game:query:myTop10");
        yield gameModelAssembler.toCollectionModel(gameService.listLast10());
      }
      case "top10" -> gameModelAssembler.toCollectionModel(gameService.listLast10());
      default -> throw new UnauthorizedException("Query games without filter is forbidden");
    };
  }

  @PostMapping
  public GameModel startNewGame() {
    return gameModelAssembler.toModel(gameService.startNewGame());
  }

  @GetMapping("/{gameId}")
  public GameModel showGame(@PathVariable String gameId) {
    return gameModelAssembler.toModel(gameService.getGame(gameId));
  }

  @PostMapping("/{gameId}/guesses")
  public ResponseEntity<?> makeGuess(@PathVariable String gameId, @RequestParam String guess) {
    try {
      var game = gameModelAssembler.toModel(gameService.makeGuess(gameId, guess));
      return ResponseEntity.ok(game);
    } catch (UnknownWordException e) {
      return ResponseEntity.badRequest().body(new Error("unknown-word", guess, e.getMessage()));
    }
  }
}
