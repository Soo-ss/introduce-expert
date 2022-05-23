import axios from "axios";

const AUTH_USER = "auth/AUTH_USER" as const;

export const authUser = (dataToSubmit: any) => {
  const request = axios
    .post("/api/auth", dataToSubmit, {
      headers: { "content-type": "application/json" },
    })
    .then((res) => res.data);

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
