import axios from "axios";
import React from "react";
import { POST_GENERATE } from "../../constant/api";

function GenerateExpertClass() {
  const body = {
    groupCategory: 1,
    category: 2,
    star: 3.7,
    price: 30000,
    title: "잔치국수",
    content: "당근과 김치와 오이와 호박의 조합",
  };
  const submitButton = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    
    axios.post(POST_GENERATE, body).then((res) => {
      console.log(res.data);
      if (res.data.success) {
        return window.location.replace("/");
      } else {
      }
    });
  };

  return (
    <div>
      <h1>Expert Class 생성</h1>
      <button onClick={submitButton}>클래스 생성하기 테스트</button>
    </div>
  );
}

export default GenerateExpertClass;
