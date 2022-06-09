import axios from "axios";
import React, { useState } from "react";
import SettingsBar from "../../partials/SettingsBar";
import useReduxAuth from "../../redux/hooks/useReduxAuth";

function SettingNotifications() {
  const { authInfo } = useReduxAuth();
  const [studyCreatedByEmail, setStudyCreatedByEmail] = useState(false);
  const [studyCreatedByWeb, setStudyCreatedByWeb] = useState(true);
  const [studyEnrollmentResultByEmail, setStudyEnrollmentResultByEmail] =
    useState(false);
  const [studyEnrollmentResultByWeb, setStudyEnrollmentResultByWeb] =
    useState(true);
  const [studyUpdatedByEmail, setStudyUpdatedByEmail] = useState(false);
  const [studyUpdatedByWeb, setStudyUpdatedByWeb] = useState(true);

  function onSubmit() {
    const body = {
      email: authInfo.email,
      studyCreatedByEmail: studyCreatedByEmail,
      studyCreatedByWeb: studyCreatedByWeb,
      studyEnrollmentResultByEmail: studyEnrollmentResultByEmail,
      studyEnrollmentResultByWeb: studyEnrollmentResultByWeb,
      studyUpdatedByEmail: studyUpdatedByEmail,
      studyUpdatedByWeb: studyUpdatedByWeb,
    };

    axios
      .post("/api/settings/notifications", body, {
        headers: { "content-type": "application/json" },
      })
      .then((res) => {
        console.log(res.data);
      });
  }

  return (
    <div>
      <h1>알림 설정</h1>
      <SettingsBar />
      <div>
        <h2>
          관심있는 주제의 스터디가 만들어졌을 때 알림을 받을 방법을 선택하세요
        </h2>
        이메일로 받기:{" "}
        <input
          type="checkbox"
          defaultChecked={studyCreatedByEmail}
          onChange={() => setStudyCreatedByEmail(!studyCreatedByEmail)}
        />
        웹으로 받기:{" "}
        <input
          type="checkbox"
          defaultChecked={studyCreatedByWeb}
          onChange={() => setStudyCreatedByWeb(!studyCreatedByWeb)}
        />
      </div>
      <div>
        <h2>스터디 모임 참가 신청 결과 알림을 받을 방법을 설정하세요</h2>
        이메일로 받기:{" "}
        <input
          type="checkbox"
          defaultChecked={studyEnrollmentResultByEmail}
          onChange={() =>
            setStudyEnrollmentResultByEmail(!studyEnrollmentResultByEmail)
          }
        />
        웹으로 받기:{" "}
        <input
          type="checkbox"
          defaultChecked={studyEnrollmentResultByWeb}
          onChange={() =>
            setStudyEnrollmentResultByWeb(!studyEnrollmentResultByWeb)
          }
        />
      </div>
      <div>
        <h2>참여중인 스터디에 대한 알림을 받을 방법을 선택하세요</h2>
        이메일로 받기:{" "}
        <input
          type="checkbox"
          defaultChecked={studyUpdatedByEmail}
          onChange={() => setStudyUpdatedByEmail(!studyUpdatedByEmail)}
        />
        웹으로 받기:{" "}
        <input
          type="checkbox"
          defaultChecked={studyUpdatedByWeb}
          onChange={() => setStudyUpdatedByWeb(!studyUpdatedByWeb)}
        />
      </div>
      <br />
      <button onClick={onSubmit}>저장하기</button>
    </div>
  );
}

export default SettingNotifications;
