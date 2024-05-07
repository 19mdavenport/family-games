import "./App.css";
import {BrowserRouter, Navigate, Route, Routes, useLocation,} from "react-router-dom";
import Login from "./components/authentication/login/Login";
import Register from "./components/authentication/register/Register";
import useUserInfo from "./hooks/userInfo/UserInfoHook";
import Toaster from "./hooks/toaster/Toaster";
import {AuthView} from "./presenter/auth/AuthPresenter";
import {LoginPresenter} from "./presenter/auth/LoginPresenter";
import {RegisterPresenter} from "./presenter/auth/RegisterPresenter";
import MainLayout from "./components/mainLayout/MainLayout";
import SevenCardRummy from "./components/game/card/sevenCardRummy/SevenCardRummy";
import {SevenCardRummyPresenter} from "./presenter/game/card/sevenCardRummy/SevenCardRummyPresenter";


const App = () => {
  const {currentUser, authToken} = useUserInfo();

  const isAuthenticated = (): boolean => {
    return !!currentUser && !!authToken;
  };

  return (
    <>
      <Toaster position="top-right"/>
      <BrowserRouter>
        {isAuthenticated() ? (
          <AuthenticatedRoutes/>
        ) : (
          <UnauthenticatedRoutes/>
        )}
      </BrowserRouter>
    </>
  );
};

const AuthenticatedRoutes = () => {
  return (
    <Routes>
      <Route element={<MainLayout/>}>
        <Route index element={<Navigate to="/games"/>}/>
        <Route path="games" element={
          <SevenCardRummy presenterGenerator={(view) => new SevenCardRummyPresenter(view)}/>
        }
        />
        <Route path="logout" element={<Navigate to="/login"/>}/>
        <Route path="*" element={<Navigate to="/games"/>}/>
      </Route>
    </Routes>
  );
};

const UnauthenticatedRoutes = () => {
  const location = useLocation();

  return (
    <Routes>
      <Route path="/login" element={<Login
        presenterGenerator={(view: AuthView) => new LoginPresenter(view)}/>}
      />
      <Route path="/register"
             element={<Register presenterGenerator={(view: AuthView) => new RegisterPresenter(view)}/>}
      />
      <Route path="*" element={<Login
        presenterGenerator={(view: AuthView) => new LoginPresenter(view, location.pathname)}
      />}/>
    </Routes>
  );
};

export default App;
