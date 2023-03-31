import { useState } from "react";
import { Button, Nav, Table } from "react-bootstrap";
import { FormattedDate } from "react-intl";
import Skeleton from "react-loading-skeleton";
import { useNavigate } from "react-router-dom";
import useSWR, { mutate } from "swr";
import "./App.css";
import { Can } from "./Can";
import ConfigPopup from "./ConfigPopup";
import LoginPopup from "./LoginPopup";

function App() {
  const [filter, setFilter] = useState("top10");
  const { data: last10Model } = useSWR(`/api/games?filter=${filter}`);
  const last10 = last10Model?._embedded.gameModelList;

  const { data: user } = useSWR("/api/users/current");

  const [showSignIn, setShowSignIn] = useState(false);
  const [showConfig, setShowConfig] = useState(false);

  const navigate = useNavigate();

  async function startNewGame() {
    const response = await fetch("/api/games", { method: "POST" });
    const game = await response.json();
    navigate(`/games/${game.id}`);
  }

  async function signOut() {
    await fetch("/logout");
    mutate("/api/users/current");
  }

  return (
    <div className="Index">
      <h1>Welcom {user ? user.name : "to Wordle 2023"}</h1>
      <div className="d-flex justify-content-center gap-2">
        <Button onClick={startNewGame}>Start New Game</Button>

        <Can I="read" a="config">
          <Button onClick={() => setShowConfig(true)}>
            <i class="bi bi-toggles"></i> Config
          </Button>
        </Can>
        <ConfigPopup show={showConfig} onHide={() => setShowConfig(false)} />

        {!user && (
          <Button variant="outline-primary" onClick={() => setShowSignIn(true)}>
            Login
          </Button>
        )}
        <LoginPopup show={showSignIn} onHide={() => setShowSignIn(false)} />

        {user && (
          <Button variant="outline-primary" onClick={signOut}>
            Logout
          </Button>
        )}
      </div>

      <div>
        <Nav
          variant="tabs"
          activeKey={filter}
          onSelect={(eventKey) => setFilter(eventKey)}
        >
          <Nav.Item>
            <Nav.Link eventKey="top10">Top 10</Nav.Link>
          </Nav.Item>
          <Can I="query" a="game" field="myTop10">
            <Nav.Item>
              <Nav.Link eventKey="myTop10">My Top 10</Nav.Link>
            </Nav.Item>
          </Can>
        </Nav>
        <Table striped className="Index-Last10">
          <thead>
            <th>Дата</th>
            <th>Резултат</th>
            <th>Опити</th>
            <th>Дума</th>
          </thead>
          <tbody>
            {last10
              ? last10.map((game) => (
                  <tr key={game.id}>
                    <td>
                      <FormattedDate
                        value={game.startedOn}
                        dateStyle="short"
                        timeStyle="short"
                      />
                    </td>
                    <td>{game.state}</td>
                    <td>{game.guesses.length}</td>
                    <td>{game.word}</td>
                  </tr>
                ))
              : [0, 1, 2, 3, 4, 5, 6, 7, 8, 9].map((pos) => (
                  <tr key={pos}>
                    <td>
                      <Skeleton width="10rem" />
                    </td>
                    <td>
                      <Skeleton />
                    </td>
                    <td>
                      <Skeleton />
                    </td>
                    <td>
                      <Skeleton />
                    </td>
                  </tr>
                ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
}

export default App;
