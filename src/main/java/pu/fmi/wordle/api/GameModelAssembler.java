package pu.fmi.wordle.api;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
    return gameToGameModel.map(game);
  }
}
