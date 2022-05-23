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
      <h2>안녕하세요, {param.nickname} 님</h2>
      <h4>자기소개: {authInfo.bio}</h4>
      <h4>블로그 URL: {authInfo.url}</h4>
      <h4>직업: {authInfo.occupation}</h4>
      <h4>사는 곳: {authInfo.location}</h4>
      <p>프로필 이미지 ▾▾▾▾▾▾▾</p>
      <img style={{width: "20%"}} src={authInfo.profileImage} />
      <br />
      <br />
      <Link to={"/settings/profile"}>프로필 수정하기</Link>
    </div>
  );
}

export default MyProfile;
