import "./Login.css";
import "bootstrap/dist/css/bootstrap.css";
import {useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import AuthenticationFormLayout from "../AuthenticationFormLayout";
import AuthToken from "../../../model/domain/AuthToken";
import User from "../../../model/domain/User";
import AuthenticationFields from "../AuthenticationFields";
import {LoginPresenter} from "../../../presenter/auth/LoginPresenter";
import {AuthView} from "../../../presenter/auth/AuthPresenter";
import useToastListener from "../../../hooks/toaster/ToastListenerHook";
import useUserInfo from "../../../hooks/userInfo/UserInfoHook";

interface Props {
  presenterGenerator: (view: AuthView) => LoginPresenter;
}

const Login = (props: Props) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();
  const { updateUserInfo } = useUserInfo();
  const { displayErrorMessage, displayInfoMessage, clearLastInfoMessage } = useToastListener();


  const checkSubmitButtonStatus = (): boolean => {
    return !username || !password;
  };

  const listener: AuthView = {
    updateUserInfo(user: User, authToken: AuthToken, rememberMe: boolean): void {
      updateUserInfo(user, authToken, rememberMe);
    },
    displayErrorMessage: displayErrorMessage,
    navigate: navigate,
    displayInfoMessage: displayInfoMessage,
    clearLastInfoMessage: clearLastInfoMessage
  };

  const [presenter] = useState(props.presenterGenerator(listener));

  const doLogin = async () => {
    await presenter.doLogin(username, password);
  };

  const inputFieldGenerator = () => {
    return (
      <>
        <AuthenticationFields setUsername={setUsername} setPassword={setPassword} isBottomField={true} />
      </>
    );
  };

  const switchAuthenticationMethodGenerator = () => {
    return (
      <div className="mb-3">
        Not registered? <Link to="/register">Register</Link>
      </div>
    );
  };

  return (
    <AuthenticationFormLayout
      headingText="Please Sign In"
      submitButtonLabel="Sign in"
      inputFieldGenerator={inputFieldGenerator}
      setRememberMe={(value) => {presenter.rememberMe = value}}
      switchAuthenticationMethodGenerator={switchAuthenticationMethodGenerator}
      submitButtonDisabled={checkSubmitButtonStatus}
      submit={doLogin}
    />
  );
};

export default Login;
