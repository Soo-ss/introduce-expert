import axios from "axios";
import React from "react";
import { useNavigate, useLocation } from "react-router-dom";
import useReduxAuth from "../../redux/hooks/useReduxAuth";

function Login() {
  const { onAuth } = useReduxAuth();

  function onSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    let formData = new FormData(event.currentTarget);
    let email = formData.get("email") as string;
    let password = formData.get("password") as string;

    axios
      .post(
        "/api/login",
        { email, password },
        {
          headers: { "content-type": "application/json" },
        }
      )
      .then((res) => {
        const token = res.data;
        if (token !== "") {
          localStorage.setItem("x_auth", token);
          return window.location.replace("/");
        } else {
          return <div>Error!!</div>;
        }
      });
  }

  return (
    <div>
      <h1>로그인 페이지</h1>
      <form onSubmit={onSubmit}>
        <label>
          email: <input name="email" type="text" />
        </label>
        <br />
        <br />
        <label>
          password: <input name="password" type="password" />
        </label>{" "}
        <br />
        <br />
        <button type="submit" style={{ padding: 20 }}>
          로그인
        </button>
      </form>
    </div>
  );
}

export default Login;
