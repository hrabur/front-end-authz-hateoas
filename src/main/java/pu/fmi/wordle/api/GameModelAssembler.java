package pu.fmi.wordle.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import pu.fmi.wordle.model.Game;

@Component
public class GameModelAssembler extends RepresentationModelAssemblerSupport<Game, GameModel> {

  private TypeMap<Game, GameModel> gameToGameModel =
      new ModelMapper().createTypeMap(Game.class, GameModel.class);

  public GameModelAssembler() {
    super(GameApi.class, GameModel.class);
  }

  @Override
  public GameModel toModel(Game game) {
    var gameModel = gameToGameModel.map(game);
    return gameModel.add(
        linkTo(methodOn(GameApi.class).makeGuess(game.getId(), null)).withRel("makeGuess"),
        linkTo(methodOn(GameApi.class).showGame(game.getId())).withSelfRel());
  }

  @Override
  public CollectionModel<GameModel> toCollectionModel(Iterable<? extends Game> entities) {
    var gameModel = super.toCollectionModel(entities);
    gameModel.add(linkTo(methodOn(GameApi.class).startNewGame()).withRel("startNewGame"));
    return gameModel;
  }


}
