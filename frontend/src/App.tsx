import React from "react";
import { Route, Routes } from "react-router-dom";
import Layout from "./partials/Layout";
import NotFound from "./pages/NotFound";
import Auth from "./partials/Auth";
import {
  anyoneRoutes,
  loggedInRoutes,
  loggedOutRoutes,
} from "./partials/Routes";

function App() {
  return (
    <Routes>
      <Route element={<Layout />}>
        {anyoneRoutes.map((item, index) => {
          return (
            <Route
              key={index}
              path={item.path}
              element={<Auth option={null}>{item.jsxElement}</Auth>}
            />
          );
        })}
        {loggedOutRoutes.map((item, index) => {
          return (
            <Route
              key={index}
              path={item.path}
              element={<Auth option={false}>{item.jsxElement}</Auth>}
            />
          );
        })}
        {loggedInRoutes.map((item, index) => {
          return (
            <Route
              key={index}
              path={item.path}
              element={<Auth option={true}>{item.jsxElement}</Auth>}
            />
          );
        })}
        <Route path="*" element={<NotFound />} />
      </Route>
    </Routes>
  );
}

export default App;
