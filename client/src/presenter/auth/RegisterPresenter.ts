import {AuthPresenter} from "./AuthPresenter";

export class RegisterPresenter extends AuthPresenter {

  public async doRegister(firstName: string, lastName: string, alias: string, password: string, confirmPassword: string,imageBytes: Uint8Array) {
    await this.doOperation(async () => {
      if(password != confirmPassword) throw new Error("Passwords must match");
      return this.service.register(firstName, lastName, alias, password, imageBytes);
    }, () => {
      this.view.navigate("/");
    }, "register");
  };

}