import React from "react";
import { Link } from "react-router-dom";

function SettingsBar() {
  return (
    <>
      <h3>SETTINGS 메뉴바</h3>
      <ul>
        <li>
          <Link to={"/settings/profile"}>프로필</Link>
        </li>
        <li>
          <Link to={"/settings/password"}>패스워드</Link>
        </li>
        <li>
          <Link to={"/settings/notifications"}>알림</Link>
        </li>
        <li>
          <Link to={"/settings/tags"}>관심 주제</Link>
        </li>
        <li>
          <Link to={"/settings/zones"}>활동 지역</Link>
        </li>
        <li>
          <Link to={"/settings/account"}>계정</Link>
        </li>
      </ul>
    </>
  );
}

export default SettingsBar;
