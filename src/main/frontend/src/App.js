import { useState } from "react";
import { Button, Nav, Table } from "react-bootstrap";
import { FormattedDate } from "react-intl";
import Skeleton from "react-loading-skeleton";
import { useNavigate } from "react-router-dom";
import useSWR, { mutate } from "swr";
import "./App.css";
import ConfigPopup from "./ConfigPopup";
import LoginPopup from "./LoginPopup";

function App() {
  const [filter, setFilter] = useState("top10");
  const { data: user } = useSWR("/api/users/current");
  const { data: top10Model } = useSWR(user?._links[filter].href);
  const top10 = top10Model?._embedded.gameModelList;

  const [showSignIn, setShowSignIn] = useState(false);
  const [showConfig, setShowConfig] = useState(false);

  const navigate = useNavigate();
  async function startNewGame() {
    const response = await fetch("/api/games", { method: "POST" });
    const game = await response.json();
    navigate(`/games/${game.id}`);
  }

  async function signOut(link) {
    await fetch(link.href);
    mutate("/api/users/current");
  }

  return (
    <div className="Index">
      <h1>Welcome {user?.id ? user.name : "to Wordle 2023"}</h1>
      <div className="d-flex justify-content-center gap-2">
        <Button onClick={startNewGame}>Start New Game</Button>

        {user?._links.readConfig && (
          <>
            <Button onClick={() => setShowConfig(true)}>
              <i class="bi bi-toggles"></i> Config
            </Button>
            <ConfigPopup
              show={showConfig}
              onHide={() => setShowConfig(false)}
              link={user?._links.readConfig}
            />
          </>
        )}

        {user?._links.login && (
          <>
            <Button
              variant="outline-primary"
              onClick={() => setShowSignIn(true)}
            >
              Login
            </Button>
            <LoginPopup
              show={showSignIn}
              onHide={() => setShowSignIn(false)}
              link={user?._links.login}
            />
          </>
        )}

        {user?._links.logout && (
          <Button
            variant="outline-primary"
            onClick={() => signOut(user?._links.logout)}
          >
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
          {user?._links.myTop10 && (
            <Nav.Item>
              <Nav.Link eventKey="myTop10">My Top 10</Nav.Link>
            </Nav.Item>
          )}
        </Nav>
        <Table striped className="Index-top10">
          <thead>
            <th>Дата</th>
            <th>Резултат</th>
            <th>Опити</th>
            <th>Дума</th>
          </thead>
          <tbody>
            {top10
              ? top10.map((game) => (
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
              : [...Array(10).keys()].map((pos) => (
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
