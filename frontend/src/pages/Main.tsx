import axios from "axios";
import React, { useEffect } from "react";

function Main() {
  const info = {
    "nickname": "keesun",
    "email": "whiteship@email.com",
    "password": 12345678
  }

  useEffect(() => {
    axios
      .post(
        "/api/users/sign-up",
        info,
        {
          headers: { "content-type": "application/json" },
        }
      )
      .then((res) => {
        console.log(res.data);
      });
  }, []);

  return (
    <div>
      <h1>메인 페이지</h1>
    </div>
  );
}

export default Main;
