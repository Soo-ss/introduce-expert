import axios from "axios";
import React, { useEffect, useState } from "react";

function CheckEmailToken() {
  const [message, setMessage] = useState("");

  const params = new URLSearchParams(window.location.search);
  const token = params.get("token");
  const email = params.get("email");

  useEffect(() => {
    axios
      .post(
        "/api/check-email-token",
        { token, email },
        {
          headers: { "content-type": "application/json" },
        }
      )
      .then((res) => {
        console.log(res.data);
        if (res.data.error === "wrongEmail") {
          setMessage("잘못된 이메일입니다.");
        }
        if (res.data.error === "wrongToken") {
          setMessage("잘못된 토큰입니다.");
        } else {
          setMessage(
            `어서오세요, ${res.data.nickname} 님, ${res.data.numberOfUser} 번째 회원 입니다. 로그인 해주세요`
          );
        }
      });
  }, []);

  return (
    <div>
      <h1>이메일 체크 페이지</h1>
      <h3>{message}</h3>
    </div>
  );
}

export default CheckEmailToken;
