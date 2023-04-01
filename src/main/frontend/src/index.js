import "bootstrap-icons/font/bootstrap-icons.css";
import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import ReactDOM from "react-dom/client";
import { IntlProvider } from "react-intl";
import "react-loading-skeleton/dist/skeleton.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { SWRConfig } from "swr";
import App from "./App";
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
        <RouterProvider router={router} />
      </SWRConfig>
    </IntlProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
