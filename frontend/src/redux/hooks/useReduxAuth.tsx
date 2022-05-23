import { useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../modules";
import { authUser } from "../modules/authStatus";

function useReduxAuth() {
  const { authInfo } = useSelector((state: RootState) => state.auth);

  const dispatch = useDispatch();

  const onAuth = useCallback(
    (data: any) => dispatch(authUser(data)),
    [dispatch]
  );

  return {
    authInfo,
    onAuth,
  };
}

export default useReduxAuth;
