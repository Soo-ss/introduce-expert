import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";

function Register() {
  let navigate = useNavigate();
  const newUser = {
    nickname: "kms",
    email: "kms334488@naver.com",
    password: "12345678",
  };
  const submitButton = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    axios
      .post("/api/register", newUser, {
        headers: { "content-type": "application/json" },
      })
      .then((res) => {
        console.log(res.data);
        if (res.data) {
          console.log("회원가입 성공");
          navigate("/");
        } else {
          console.log("회원가입 에러");
        }
      });
  };

  return (
    <div>
      <h1>회원가입</h1>
      <h3>가입 시간이 오래걸려서 로딩 버튼 만들어야할듯</h3>
      <p>버튼 누르면 로딩중하고 버튼 비활성화</p>
      <p>
        이메일 다 보내면 서버쪽에서 ok라고 응답주면 회원가입이
        완료되었습니다라고 바꾸기
      </p>
      <button onClick={submitButton}>회원가입 버튼 테스트</button>
    </div>
  );
}

export default Register;
