package pu.fmi.wordle.api;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.hateoas.RepresentationModel;
import pu.fmi.wordle.model.Game.GameState;
import pu.fmi.wordle.model.Guess;

public class GameModel extends RepresentationModel<GameModel> {

  String id;
  String word;
  LocalDateTime startedOn;
  String alphabet;
  String alphabetMatches;
  int maxGuesses;
  List<Guess> guesses;
  GameState state;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public LocalDateTime getStartedOn() {
    return startedOn;
  }

  public void setStartedOn(LocalDateTime startedOn) {
    this.startedOn = startedOn;
  }

  public String getAlphabet() {
    return alphabet;
  }

  public void setAlphabet(String alphabet) {
    this.alphabet = alphabet;
  }

  public String getAlphabetMatches() {
    return alphabetMatches;
  }

  public void setAlphabetMatches(String alphabetMatches) {
    this.alphabetMatches = alphabetMatches;
  }

  public int getMaxGuesses() {
    return maxGuesses;
  }

  public void setMaxGuesses(int maxGuesses) {
    this.maxGuesses = maxGuesses;
  }

  public List<Guess> getGuesses() {
    return guesses;
  }

  public void setGuesses(List<Guess> guesses) {
    this.guesses = guesses;
  }

  public GameState getState() {
    return state;
  }

  public void setState(GameState state) {
    this.state = state;
  }
}
