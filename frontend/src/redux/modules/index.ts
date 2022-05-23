import { combineReducers } from "redux";
import authStatus from "./authStatus";

const rootReducer = combineReducers({
  auth: authStatus,
});

export default rootReducer;
export type RootState = ReturnType<typeof rootReducer>;