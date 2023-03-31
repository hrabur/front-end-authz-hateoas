import { createMongoAbility } from "@casl/ability";
import "bootstrap-icons/font/bootstrap-icons.css";
import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import ReactDOM from "react-dom/client";
import { IntlProvider } from "react-intl";
import "react-loading-skeleton/dist/skeleton.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import useSWR, { SWRConfig } from "swr";
import App from "./App";
import { AbilityContext } from "./Can";
import Game from "./Game";
import "./index.css";
import reportWebVitals from "./reportWebVitals";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: "/login",
    element: <App showLogin />,
  },
  {
    path: "games/:gameId",
    element: <Game />,
  },
]);

function MyAbilityContext({ children }) {
  const { data: user } = useSWR("/api/users/current");
  const rawRules = user?.permissions
    .map((permission) => permission.split(":"))
    .map((parts) =>
      parts.length === 2
        ? { subject: parts[0], action: parts[1] }
        : { subject: parts[0], action: parts[1], fields: parts[2] }
    );
  const ability = createMongoAbility(rawRules);

  return (
    <AbilityContext.Provider value={ability}>
      {children}
    </AbilityContext.Provider>
  );
}

const fetcher = async (url) => {
  const res = await fetch(url);

  if (res.status === 404) {
    return null;
  }

  if (!res.ok) {
    const error = new Error("An error occurred while fetching the data.");
    error.info = await res.json();
    error.status = res.status;
    throw error;
  }

  return res.json();
};

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <IntlProvider locale="bg">
      <SWRConfig
        value={{
          fetcher,
        }}
      >
        <MyAbilityContext>
          <RouterProvider router={router} />
        </MyAbilityContext>
      </SWRConfig>
    </IntlProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
