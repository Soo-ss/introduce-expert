import axios from "axios";
import React, { useEffect, useState } from "react";
import { POST_KAKAO_LOGIN, POST_KAKAO_REGISTER } from "../../constant/api";

function RegisterKakao() {
  const [accessToken, setAccessToken] = useState("");

  const clientId = process.env.REACT_APP_CLIENT_ID;
  const loginUrl = process.env.REACT_APP_LOGIN_URL;
  const profileUrl = process.env.REACT_APP_PROFILE_URL;
  const redirectUrl = process.env.REACT_APP_REDIRECT_URL;
  const tokenUrl = process.env.REACT_APP_TOKEN_URL;
  const baseUrl = "http://localhost:3000";
  const query = window.location.search.split("=")[1];
  console.log(query);

  useEffect(() => {
    axios
      .post(
        tokenUrl +
          "?redirect_uri=" +
          baseUrl +
          redirectUrl +
          "&" +
          `grant_type=authorization_code&client_id=${clientId}&code=${query}`,
        null,
        { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
      )
      .then((res) => {
        console.log(res.data);
        console.log(res.status);
        if (res.status === 200) {
          setAccessToken(res.data.access_token);
        } else {
          alert("Error");
        }
      });
  }, []);

  console.log(accessToken);

  function directKakaoLogin() {
    axios.post(POST_KAKAO_LOGIN + `accessToken=${accessToken}`).then((res) => {
      const token = res.data.data;
      if (token !== "") {
        localStorage.setItem("x_auth", token);
        return window.location.replace("/");
      } else {
        return <div>Error!!</div>;
      }
    });
  }

  function onSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    let formData = new FormData(event.currentTarget);
    let name = formData.get("name") as string;

    axios
      .post(POST_KAKAO_REGISTER + `name=${name}&accessToken=${accessToken}`)
      .then((res) => {
        if (res.data.success) {
          directKakaoLogin();
        }
      });
  }

  return (
    <div>
      <h1>RegisterKakao</h1>
      <form onSubmit={onSubmit}>
        <label>
          이름을 입력해주세요: <input name="name" type="text" />
        </label>
        <button type="submit" style={{ padding: 20 }}>
          카카오 계정 가입
        </button>
      </form>
    </div>
  );
}

export default RegisterKakao;
