import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Main from "../pages/Main";
import SignIn from "../pages/account/Login";
import useReduxAuth from "../redux/hooks/useReduxAuth";

// 여기서 auth를 계속 쏘게 만들어서 동작하게한다. (얘가 hoc역할)
// redux는 새로고침하면 리셋되기때문에 이렇게 해야함.
function Auth({
  children,
  option,
}: {
  children: JSX.Element;
  option: boolean | null;
}) {
  let navigate = useNavigate();
  const { onAuth, authInfo } = useReduxAuth();

  useEffect(() => {
    const token = localStorage.getItem("x_auth");
    onAuth();
    console.log(authInfo);
  }, []);

  switch (option) {
    // 아무나 출입 가능. 그래도 api를 계속 쏴야하기때문에 Auth로 감싸준다.
    case null:
      break;

    // 인증 안됐거나 로그아웃한 유저만 팅구면 된다.
    case true:
      // 이메일 인증 안된 사용자는 홈으로
      if (!authInfo.emailVerified) {
        console.log("이메일 인증하세요");
        return <Main />;
      }

      // 인증 안됐다면 로그아웃 상태
      if (!authInfo.isAuth) {
        console.log("로그인 하세요");
        return <SignIn />;
      }
      break;

    // 로그인한 유저만 팅구면 된다.
    case false:
      if (authInfo.isAuth) {
        console.log("이미 로그인 된 유저입니다.");
        return <Main />;
      }
      break;
  }

  // 조건 다 통과하면 그냥 원래페이지 return.
  return children;
}

export default Auth;
