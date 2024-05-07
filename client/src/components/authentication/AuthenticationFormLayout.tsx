import React from "react";

interface Props {
  headingText: string;
  submitButtonLabel: string;
  inputFieldGenerator: () => React.ReactElement;
  switchAuthenticationMethodGenerator: () => React.ReactElement;
  setRememberMe: (value: boolean) => void;
  submitButtonDisabled: () => boolean;
  submit: () => void;
}

const AuthenticationFormLayout = (props: Props) => {

  return (
    <div>
      <div className="center">
        <div className="form-main w-100 m-auto rounded">
          <form>
            <img
              className="mb-4"
              src="/FIXME_LOGO.png"
              alt=""
              width="72"
              height="72"
            />
            <h1 className="h3 mb-3 fw-normal">{props.headingText}</h1>

            {props.inputFieldGenerator()}

            <div className="checkbox mb-3">
              <label>
                <input
                  type="checkbox"
                  value="remember-me"
                  onChange={(event) =>
                    props.setRememberMe(event.target.checked)
                  }
                />{" "}
                Remember me
              </label>
            </div>

            {props.switchAuthenticationMethodGenerator()}

            <button
              id="submitButton"
              className="w-100 btn btn-lg btn-primary"
              type="button"
              disabled={props.submitButtonDisabled()}
              onClick={() => props.submit()}
            >
              {props.submitButtonLabel}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AuthenticationFormLayout;
