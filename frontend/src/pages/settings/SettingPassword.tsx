import axios from "axios";
import React, { useState } from "react";
import SettingsBar from "../../partials/SettingsBar";
import useReduxAuth from "../../redux/hooks/useReduxAuth";

function SettingPassword() {
  const { authInfo } = useReduxAuth();
  const [passwordData, setPasswordData] = useState({
    newPassword: "",
    newPasswordConfirm: "",
  });

  function handleChange(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    let formData = new FormData(event.currentTarget);
    let newPassword = formData.get("newPassword") as string;
    let newPasswordConfirm = formData.get("newPasswordConfirm") as string;

    setPasswordData({ newPassword, newPasswordConfirm });
  }

  function onSubmit() {
    const body = {
      email: authInfo.email,
      newPassword: passwordData.newPassword,
      newPasswordConfirm: passwordData.newPasswordConfirm,
    };

    axios
      .post("/api/settings/password", body, {
        headers: { "content-type": "application/json" },
      })
      .then((res) => {
        console.log(res.data);
      });
  }

  function renderChangeButton() {
    if (
      passwordData.newPassword === "" ||
      passwordData.newPasswordConfirm === ""
    ) {
      return <p style={{ color: "red" }}>패스워드를 입력해주세요.</p>;
    } else if (passwordData.newPassword !== passwordData.newPasswordConfirm) {
      return <p style={{ color: "red" }}>패스워드가 다릅니다.</p>;
    }
    return <button onClick={onSubmit}>수정하기</button>;
  }

  return (
    <div>
      <h1>패스워드 변경</h1>
      <SettingsBar />
      <form onChange={handleChange}>
        새 패스워드: <input name="newPassword" type="password" />
        <br />
        새 패스워드 확인: <input name="newPasswordConfirm" type="password" />
      </form>
      {renderChangeButton()}
    </div>
  );
}

export default SettingPassword;
