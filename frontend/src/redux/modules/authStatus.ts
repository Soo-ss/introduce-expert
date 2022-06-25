import axios from "axios";
import { GET_API_USER } from "../../constant/api";

const AUTH_USER = "auth/AUTH_USER" as const;

export const authUser = () => {
  const request = axios.get(GET_API_USER).then((res) => {
    console.log(res.data);
    return res.data;
  });

  return {
    type: AUTH_USER,
    payload: request,
  };
};

type AuthAction = ReturnType<typeof authUser>;

type AuthState = {
  authInfo: any;
};

const initalState: AuthState = {
  authInfo: {},
};

function authStatus(state = initalState, action: AuthAction) {
  switch (action.type) {
    case AUTH_USER:
      return { ...state, authInfo: action.payload };
    default:
      return state;
  }
}

export default authStatus;
