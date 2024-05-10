import {AuthPresenter, AuthView} from "./AuthPresenter";


export class LoginPresenter extends AuthPresenter {
  private readonly originalUrl: string | undefined;

  constructor(view: AuthView, originalUrl?: string) {
    super(view);
    this.originalUrl = originalUrl;
  }

  public async doLogin(alias: string, password: string) {
    await this.doOperation(async () => {
      return this.service.login(alias, password);
    }, () => {
      if (!!this.originalUrl) this.view.navigate(this.originalUrl);
      else this.view.navigate("/");
    }, "log user in");
  };
}