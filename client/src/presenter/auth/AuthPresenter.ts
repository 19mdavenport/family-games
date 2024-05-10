import {UserService} from "../../model/service/UserService";
import User from "../../model/domain/User";
import AuthToken from "../../model/domain/AuthToken";
import {MessagePresenter, MessageView} from "../MessagePresenter";

export interface AuthView extends MessageView {
    updateUserInfo(user: User, authToken: AuthToken, rememberMe: boolean): void;
    navigate: (url: string) => void;
}

export abstract class AuthPresenter extends MessagePresenter<AuthView> {
    private _rememberMe: boolean;
    private readonly _service: UserService;

    public constructor(view: AuthView) {
        super(view);
        this._service = new UserService();
        this._rememberMe = false;
    }

    protected get service(): UserService {
        return this._service;
    }

    public get rememberMe(): boolean {
        return this._rememberMe;
    }

    public set rememberMe(value: boolean) {
        this._rememberMe = value;
    }

    public async doOperation(serviceCall: () => Promise<[User, AuthToken]>, navigate: () => void, description: string) {
        await this.doFailureReportingOperation(async () => {
            let [user, authToken] = await serviceCall();
            this.view.updateUserInfo(user, authToken, this.rememberMe);
            navigate();
        }, description)
    };
}