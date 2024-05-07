import React, {Context, createContext, useState} from "react";
import User from "../../model/domain/User";
import AuthToken from "../../model/domain/AuthToken";

const CURRENT_USER_KEY: string = "CurrentUserKey";
const AUTH_TOKEN_KEY: string = "AuthTokenKey";

interface UserInfo {
  currentUser: User | null;
  authToken: AuthToken | null;
  updateUserInfo: (
    currentUser: User,
    authToken: AuthToken,
    rememberMe: boolean
  ) => void;
  clearUserInfo: () => void;
}

const defaultUserInfo: UserInfo = {
  currentUser: null,
  authToken: null,
  updateUserInfo: () => null,
  clearUserInfo: () => null
};

export const UserInfoContext: Context<UserInfo> =
  createContext<UserInfo>(defaultUserInfo);

interface Props {
  children: React.ReactNode;
}

const UserInfoProvider: React.FC<Props> = ({children}) => {
  const saveToStorage = (
    storage: Storage,
    currentUser: User,
    authToken: AuthToken
  ): void => {
    storage.setItem(CURRENT_USER_KEY, currentUser.toJson());
    storage.setItem(AUTH_TOKEN_KEY, authToken.toJson());
  };

  const retrieveFromStorage = (): {
    currentUser: User | null;
    authToken: AuthToken | null;
  } => {
    let loggedInUser = User.fromJson(localStorage.getItem(CURRENT_USER_KEY));
    let authToken = AuthToken.fromJson(localStorage.getItem(AUTH_TOKEN_KEY));

    if (!loggedInUser || !authToken) {
      loggedInUser = User.fromJson(sessionStorage.getItem(CURRENT_USER_KEY));
      authToken = AuthToken.fromJson(sessionStorage.getItem(AUTH_TOKEN_KEY));
    }

    return {
      currentUser: loggedInUser,
      authToken: authToken,
    };
  };

  const clearStorage = (): void => {
    localStorage.removeItem(CURRENT_USER_KEY);
    localStorage.removeItem(AUTH_TOKEN_KEY);
    sessionStorage.removeItem(CURRENT_USER_KEY);
    sessionStorage.removeItem(AUTH_TOKEN_KEY);
  };

  const [userInfo, setUserInfo] = useState({
    ...defaultUserInfo,
    ...retrieveFromStorage(),
  });

  const updateUserInfo = (
    currentUser: User,
    authToken: AuthToken,
    remember: boolean
  ) => {
    setUserInfo({
      ...userInfo,
      currentUser: currentUser,
      authToken: authToken,
    });

    saveToStorage(remember ? localStorage : sessionStorage, currentUser, authToken);
  };

  const clearUserInfo = () => {
    setUserInfo({
      ...userInfo,
      currentUser: null,
      authToken: null,
    });
    clearStorage();
  };


  return (
    <UserInfoContext.Provider
      value={{
        ...userInfo,
        updateUserInfo: updateUserInfo,
        clearUserInfo: clearUserInfo,
      }}
    >
      {children}
    </UserInfoContext.Provider>
  );
};

export default UserInfoProvider;
