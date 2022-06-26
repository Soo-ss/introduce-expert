import axios from "axios";
import React, { useEffect } from "react";

function LoginKakao() {
  const clientId = process.env.REACT_APP_CLIENT_ID;
  const loginUrl = process.env.REACT_APP_LOGIN_URL;
  const profileUrl = process.env.REACT_APP_PROFILE_URL;
  const redirectUrl = process.env.REACT_APP_REDIRECT_URL;
  const tokenUrl = process.env.REACT_APP_TOKEN_URL;
  const baseUrl = "http://localhost:3000";

  let loginUrlString =
    loginUrl +
    "?client_id=" +
    clientId +
    "&response_type=code" +
    "&redirect_uri=" +
    baseUrl +
    redirectUrl;

  function popupKakaoLogin(event: React.MouseEvent<HTMLButtonElement>) {
    event.preventDefault();

    window.open(
      loginUrlString,
      "popupKakaoLogin",
      "width=700,height=500,scrollbars=0,toolbar=0,menubar=no"
    );
  }
  return (
    <div>
      <h1>LoginKakao</h1>
      <button onClick={popupKakaoLogin}>카카오 계정으로 로그인하기</button>
    </div>
  );
}

export default LoginKakao;
