package pu.fmi.wordle.model;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import pu.fmi.wordle.model.Game.GameState;

public interface GameRepository extends JpaRepository<Game, String> {
  // TODO: At the moment the method returns all games with state not equal to the given one.
  // Rename the method to return the last 10 games with state not equal to the given one ordered
  // by startedOn descending
  Collection<Game> findTop10ByStateNotOrderByStartedOnDesc(GameState state);

  // TODO: Create method that returns all games with the given status that are started before the
  // given time
  Collection<Game> findByStateAndStartedOnBefore(GameState state, LocalDateTime startedOn);
}
