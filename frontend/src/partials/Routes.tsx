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

export const anyoneRoutes = [
  {
    path: "/",
    jsxElement: <Main />,
  },
  {
    path: "check-email-token",
    jsxElement: <CheckEmailToken />,
  },
];

export const loggedOutRoutes = [
  {
    path: "login",
    jsxElement: <Login />,
  },
  {
    path: "register",
    jsxElement: <Register />,
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
];
