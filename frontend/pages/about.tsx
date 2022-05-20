import axios from "axios";
import { useEffect } from "react";
import Seo from "../components/Seo";

export default function About() {
  const testForm = {
    email: "email@email.com",
    password: "54321",
    value: 3463456,
  };

  function buttonTest(event: React.MouseEvent<HTMLButtonElement>) {
    event.preventDefault();
    axios.post("/api/get-data", testForm).then((res) => {
      console.log(res.data);
    });
  }

  // useEffect(() => {
  //   axios.post("/api/get-data", testForm).then((res) => {
  //     console.log(res.data);
  //   });
  // }, []);

  return (
    <div>
      <Seo title="About" />
      <h1>About page</h1>
      <button onClick={buttonTest}>버튼 테스트</button>
    </div>
  );
}
