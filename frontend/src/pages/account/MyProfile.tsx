import React from "react";
import { Link, useParams } from "react-router-dom";
import SettingsBar from "../../partials/SettingsBar";
import useReduxAuth from "../../redux/hooks/useReduxAuth";

function MyProfile() {
  const { authInfo } = useReduxAuth();
  const param = useParams();

  return (
    <div>
      <h1>나의 프로필 페이지</h1>
      <SettingsBar />
      <p>프로필 이미지 ▾▾▾▾▾▾▾</p>
      <img style={{ width: "20%" }} src={authInfo.profileImage} />
      <br />
      <br />
      <li>
        <Link to={"/expertOnly/generate"}>클래스 생성하기</Link>
      </li>
      <li>
        <Link to={"/settings/profile"}>프로필 수정하기</Link>
      </li>
    </div>
  );
}

export default MyProfile;
