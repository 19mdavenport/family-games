import AuthToken from "../../model/domain/AuthToken";
import {UserService} from "../../model/service/UserService";
import {MessagePresenter, MessageView} from "../MessagePresenter";

export interface NavbarView extends MessageView {
  clearUserInfo(): void;
  navigate(url: string): void;
}

export class NavbarPresenter extends MessagePresenter<NavbarView> {
  private _service: UserService | null = null;

  constructor(view: NavbarView) {
    super(view);
  }

  public get service() {
    if(!this._service) {
      this._service = new UserService()
    }
    return this._service;
  }

  public async logOut(authToken: AuthToken) {
    this.view.displayInfoMessage("Logging Out...", 0);

    await this.doFailureReportingOperation(async () => {
      await this.service.logout(authToken);

      this.view.clearLastInfoMessage();
      this.view.clearUserInfo();
      this.view.navigate("/login");
    }, "log user out");
  };

}