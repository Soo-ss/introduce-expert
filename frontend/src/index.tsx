import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { createStore, applyMiddleware } from "redux";
import { Provider } from "react-redux";
import ReduxThunk from "redux-thunk";
import promiseMiddleware from "redux-promise";
import rootReducer from "./redux/modules";
import axios from "axios";

const createStoreWithMiddleware = applyMiddleware(
  promiseMiddleware,
  ReduxThunk
)(createStore);

// const token = localStorage.getItem("x_auth");
// axios.defaults.headers.common["X-AUTH-TOKEN"] = token!;

ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <Provider
        store={createStoreWithMiddleware(
          rootReducer,
          (window as any).__REDUX_DEVTOOLS_EXTENSION__ &&
            (window as any).__REDUX_DEVTOOLS_EXTENSION__()
        )}>
        <App />
      </Provider>
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
