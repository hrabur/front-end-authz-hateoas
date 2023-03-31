import classNames from "classnames";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import "./Game.css";
import Guess from "./Guess";

function useWindowListener(eventType, listener) {
  useEffect(() => {
    window.addEventListener(eventType, listener);
    return () => {
      window.removeEventListener(eventType, listener);
    };
  }, [eventType, listener]);
}

function Game() {
  let { gameId } = useParams();
  const [game, setGame] = useState({
    word: "",
    alphabet: "",
    state: "ONGOING",
    maxGuesses: 5,
    guesses: [],
  });

  useEffect(() => {
    const fetchGame = async () => {
      const response = await fetch(`/api/games/${gameId}`);
      const game = await response.json();
      setGame(game);
    };

    fetchGame();
  }, [gameId]);

  const [word, setWord] = useState("");
  const [error, setError] = useState();

  async function makeGuess() {
    const formData = new FormData();
    formData.append("guess", word);
    const response = await fetch(`/api/games/${gameId}/guesses`, {
      method: "POST",
      body: formData,
    });

    const json = await response.json();
    if (response.status === 200) {
      setGame(json);
      setError(null);
    } else {
      setError(json);
    }

    setWord("");
  }

  function handleKeyPress({ key }) {
    if (word.length < 5 && game.alphabet.includes(key)) {
      setWord((old) => old + key);
    } else if (word.length > 0 && key === "Backspace") {
      setWord((old) => old.slice(0, -1));
    } else if (word.length === 5 && key === "Enter") {
      makeGuess();
    }
  }

  useWindowListener("keydown", handleKeyPress);

  const guessesLeft = game.maxGuesses - game.guesses.length;
  const placeholders =
    guessesLeft > 0
      ? [
          <Guess id={-1} word={word} />,
          ...[...Array(guessesLeft - 1).keys()].map((gpIdx) => (
            <div key={`guess-placeholder-${gpIdx}`}>
              <Guess id="gpIdx" />
            </div>
          )),
        ]
      : [];

  return (
    <div className="Wordle">
      {game.state === "WIN" && (
        <h1 className="Wordle-Message">
          Браво! Позна думата с {game.guesses.length} опита 🎉
          <Link to={"/"}>Играй отновo</Link>
        </h1>
      )}

      {game.state === "LOSS" && (
        <h1 className="Wordle-Message">
          Жалко! Не успя да познаеш думата 😢
          <Link to={"/"}>Опитай пак</Link>
        </h1>
      )}

      <form onSubmit={makeGuess} method="POST">
        <div>
          {game.guesses.map((guess) => (
            <div key={`guess-${guess.id}`}>
              <Guess {...guess} />
            </div>
          ))}
          {placeholders}
        </div>
        {game.state === "ONGOING" && error?.code === "unknown-word" && (
          <p className="Wordle-Error">В речника няма дума "{error.value}"</p>
        )}
      </form>
      <div>
        <div className="Wordle-Keyboard">
          {[...game.alphabet].map((char, idx) => (
            <button
              className={classNames(
                "Wordle-Key",
                `Wordle-Match_${game.alphabetMatches.charAt(idx)}`
              )}
              onClick={() => handleKeyPress({ key: char })}
            >
              {char}
            </button>
          ))}
          <button
            className="Wordle-Key Wordle-Backspace"
            onClick={() => handleKeyPress({ key: "Backspace" })}
          >
            ⌫
          </button>
          <button
            className="Wordle-Key Wordle-Enter"
            onClick={() => handleKeyPress({ key: "Enter" })}
          >
            ↵
          </button>
        </div>
      </div>
    </div>
  );
}

export default Game;
