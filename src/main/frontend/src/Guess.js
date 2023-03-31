import classNames from "classnames";

export default function Guess({ id, word, matches }) {
  const padded = (word ?? "").padEnd(5, "\u00A0");
  return [...padded].map((char, idx) => (
    <span
      key={`guess-${id}-${idx}`}
      className={classNames("Wordle-Match", {
        [`Wordle-Match_${matches?.charAt(idx)}`]: matches,
      })}
    >
      {char}
    </span>
  ));
}
