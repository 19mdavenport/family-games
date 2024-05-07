import "./Register.css";
import "bootstrap/dist/css/bootstrap.css";
import {ChangeEvent, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import AuthenticationFormLayout from "../AuthenticationFormLayout";
import AuthToken from "../../../model/domain/AuthToken";
import AuthenticationFields from "../AuthenticationFields";
import {AuthView} from "../../../presenter/auth/AuthPresenter";
import {RegisterPresenter} from "../../../presenter/auth/RegisterPresenter";
import User from "../../../model/domain/User";
import useUserInfo from "../../../hooks/userInfo/UserInfoHook";
import useToastListener from "../../../hooks/toaster/ToastListenerHook";

interface Props {
  presenterGenerator: (view: AuthView) => RegisterPresenter;
}

const Register = (props: Props) => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [imageBytes, setImageBytes] = useState<Uint8Array>(new Uint8Array());
  const [imageUrl, setImageUrl] = useState<string>("");

  const navigate = useNavigate();
  const { updateUserInfo } = useUserInfo();
  const { displayErrorMessage, displayInfoMessage, clearLastInfoMessage } = useToastListener();

  const checkSubmitButtonStatus = (): boolean => {
    return !firstName || !lastName || !username || !password || !imageUrl;
  };

  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    handleImageFile(file);
  };

  const handleImageFile = (file: File | undefined) => {
    if (file) {
      setImageUrl(URL.createObjectURL(file));

      const reader = new FileReader();
      reader.onload = (event: ProgressEvent<FileReader>) => {
        const imageStringBase64 = event.target?.result as string;

        // Remove unnecessary file metadata from the start of the string.
        const imageStringBase64BufferContents =
          imageStringBase64.split("base64,")[1];

        const bytes: Uint8Array = Buffer.from(
          imageStringBase64BufferContents,
          "base64"
        );

        setImageBytes(bytes);
      };
      reader.readAsDataURL(file);
    } else {
      setImageUrl("");
      setImageBytes(new Uint8Array());
    }
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

  const doRegister = async () => {
    presenter.doRegister(firstName, lastName, username, password, confirmPassword, imageBytes);
  };

  const inputFieldGenerator = () => {
    return (
      <>
        <div className="form-floating">
          <input
            type="text"
            className="form-control"
            size={50}
            id="firstNameInput"
            placeholder="First Name"
            onChange={(event) => setFirstName(event.target.value)}
          />
          <label htmlFor="firstNameInput">First Name</label>
        </div>
        <div className="form-floating">
          <input
            type="text"
            className="form-control"
            size={50}
            id="lastNameInput"
            placeholder="Last Name"
            onChange={(event) => setLastName(event.target.value)}
          />
          <label htmlFor="lastNameInput">Last Name</label>
        </div>
        <AuthenticationFields setUsername={setUsername} setPassword={setPassword} isBottomField={false}/>
        <div className="form-floating">
          <input
            type="password"
            className="form-control"
            size={50}
            id="confirmPasswordInput"
            placeholder="Confirm Password"
            onChange={(event) => setConfirmPassword(event.target.value)}
          />
          <label htmlFor="confirmPasswordInput">Confirm Password</label>
        </div>
        <div className="form-floating mb-3">
          <input
            type="file"
            className="d-inline-block py-5 px-4 form-control bottom"
            id="imageFileInput"
            onChange={handleFileChange}
          />
          <label htmlFor="imageFileInput">User Image</label>
          {imageUrl && <img src={imageUrl} className="img-thumbnail" alt=""></img>}
        </div>
      </>
    );
  };

  const switchAuthenticationMethodGenerator = () => {
    return (
      <div className="mb-3">
        Already registered? <Link to="/login">Sign in</Link>
      </div>
    );
  };

  return (
    <AuthenticationFormLayout
      headingText="Please Register"
      submitButtonLabel="Register"
      inputFieldGenerator={inputFieldGenerator}
      setRememberMe={(value) => {presenter.rememberMe = value}}
      switchAuthenticationMethodGenerator={switchAuthenticationMethodGenerator}
      submitButtonDisabled={checkSubmitButtonStatus}
      submit={doRegister}
    />
  );
};

export default Register;
