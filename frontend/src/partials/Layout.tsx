import axios from "axios";
import React, { useEffect } from "react";
import { Link, Outlet, useNavigate } from "react-router-dom";
import useReduxAuth from "../redux/hooks/useReduxAuth";

function Layout() {
  const { authInfo } = useReduxAuth();

  const onLogout = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    const token = localStorage.getItem("x_auth");

    axios
      .post(
        "/api/logout",
        { token },
        {
          headers: { "content-type": "application/json" },
        }
      )
      .then((res) => {
        if (res.data) {
          console.log("로그아웃 하셨습니다.");
          localStorage.removeItem("x_auth");
          return window.location.replace("/");
        } else {
          console.log("로그아웃 실패");
        }
      });
  };

  // 로그아웃 상태
  if (!authInfo.success) {
    return (
      <div>
        <h1>Introduce Expert</h1>
        <nav>
          <li>
            <a href="/">메인 페이지</a>
          </li>
          <li>
            <a href="/login">로그인 페이지</a>
          </li>
          <li>
            <a href="/register">회원가입 페이지</a>
          </li>
          <li>
            <a href="/registerExpert">엑스퍼트 회원가입 페이지</a>
          </li>
          <li>
            <a href="/loginKakao">카카오 계정으로 로그인 페이지</a>
          </li>
        </nav>
        <div className="content">
          <Outlet />
        </div>
      </div>
    );
  }
  // 로그인 상태
  return (
    <div>
      <h1>Introduce Expert</h1>
      <nav>
        <li>
          <button onClick={onLogout}>로그아웃 버튼</button>
        </li>
        <li>
          <a href="/">메인 페이지</a>
        </li>
        <li>
          <a href="/protected">로그인 한 사람만</a>
        </li>
        <li>
          <a href={`/profile/${authInfo.nickname}`}>나의 프로필</a>
        </li>
      </nav>
      {/* {authInfo.emailVerified ? (
        ""
      ) : (
        <div>
          <h3 style={{ color: "red" }}>이메일 인증을 해주세요</h3>
        </div>
      )} */}
      <div className="content">
        <Outlet />
      </div>
    </div>
  );
}

export default Layout;
