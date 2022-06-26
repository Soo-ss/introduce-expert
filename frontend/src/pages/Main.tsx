import axios from "axios";
import React, { useEffect, useState } from "react";
import { GET_ALL_EXPERT_CLASS } from "../constant/api";

// 조사하기
interface IList {
  account: {
    accountId: number;
    email: string;
    name: string;
  };
  category: number;
  content: string;
  createdAt: Date;
  expertClassId: number;
  groupCategory: number;
  modifiedAt: Date;
  price: number;
  star: number;
  title: string;
}

function Main() {
  const [list, setList] = useState([]);

  useEffect(() => {
    axios.get(GET_ALL_EXPERT_CLASS).then((res) => {
      console.log(res.data.list);
      if (res.data.success) {
        setList(res.data.list);
      }
    });
  }, []);

  return (
    <div>
      <h1>메인 페이지</h1>
      {list.map((value: any, index) => {
        return (
          <div key={index} style={{ border: "1px solid blue", float: "left" }}>
            <ul>
              <li>{value.groupCategory}</li>
              <li>{value.category}</li>
              <li>{value.content}</li>
              <li>{value.createdAt}</li>
              <li>{value.expertClassId}</li>
              <li>{value.modifiedAt}</li>
              <li>{value.price}</li>
              <li>{value.star}</li>
              <li>{value.title}</li>
              <li>{value.account.name}</li>
            </ul>
          </div>
        );
      })}
    </div>
  );
}

export default Main;
