import CheckEmailToken from "../pages/account/CheckEmailToken";
import MyProfile from "../pages/account/MyProfile";
import Login from "../pages/account/Login";
import Register from "../pages/account/Register";
import Main from "../pages/Main";
import Protected from "../pages/Protected";
import SettingAccount from "../pages/settings/SettingAccount";
import SettingNotifications from "../pages/settings/SettingNotifications";
import SettingPassword from "../pages/settings/SettingPassword";
import SettingProfile from "../pages/settings/SettingProfile";
import DetailExpertClass from "../pages/expertClass/DetailExpertClass";
import LoginKakao from "../pages/kakao/LoginKakao";
import RegisterExpert from "../pages/expertOnly/RegisterExpert";
import GenerateExpertClass from "../pages/expertOnly/GenerateExpertClass";
import RegisterKakao from "../pages/kakao/RegisterKakao";

// export const anyoneRoutes = [
//   {
//     path: "/",
//     jsxElement: <Main />,
//   },
//   {
//     path: "check-email-token",
//     jsxElement: <CheckEmailToken />,
//   },
// ];

export const loggedOutRoutes = [
  {
    path: "/",
    jsxElement: <Main />,
  },
  {
    path: "check-email-token",
    jsxElement: <CheckEmailToken />,
  },
  {
    path: "login",
    jsxElement: <Login />,
  },
  {
    path: "register",
    jsxElement: <Register />,
  },
  {
    path: "loginKakao",
    jsxElement: <LoginKakao />,
  },
  {
    path: "registerExpert",
    jsxElement: <RegisterExpert />,
  },
  {
    path: "social/login/kakao",
    jsxElement: <RegisterKakao />,
  },
];

export const loggedInRoutes = [
  {
    path: "protected",
    jsxElement: <Protected />,
  },
  {
    path: "profile/:nickname",
    jsxElement: <MyProfile />,
  },
  {
    path: "settings/profile",
    jsxElement: <SettingProfile />,
  },
  {
    path: "settings/password",
    jsxElement: <SettingPassword />,
  },
  {
    path: "settings/notifications",
    jsxElement: <SettingNotifications />,
  },
  {
    path: "settings/account",
    jsxElement: <SettingAccount />,
  },
  {
    path: "expertOnly/detail",
    jsxElement: <DetailExpertClass />,
  },
  {
    path: "expertOnly/generate",
    jsxElement: <GenerateExpertClass />,
  },
];
