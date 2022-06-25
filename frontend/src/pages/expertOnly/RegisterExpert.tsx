import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { POST_EXPERT_ONLY_REGISTER, POST_REGISTER } from "../../constant/api";

function RegisterExpert() {
  const newUser = {
    email: "admin@admin.com",
    password: "1234",
    name: "admin",
  };
  const submitButton = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    axios.post(POST_EXPERT_ONLY_REGISTER, newUser).then((res) => {
      console.log(res.data);
      if (res.data.success) {
        return window.location.replace("/");
      } else {
      }
    });
  };

  return (
    <div>
      <h1>엑스퍼트 회원가입</h1>
      <button onClick={submitButton}>회원가입 버튼 테스트</button>
    </div>
  );
}

export default RegisterExpert;
