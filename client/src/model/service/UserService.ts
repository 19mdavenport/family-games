import {FakeData} from "../../fake/FakeData";
import User from "../domain/User";
import AuthToken from "../domain/AuthToken";

export class UserService {
  public async login(username: string, password: string): Promise<[User, AuthToken]> {
    // try {
    //   const request = new LoginRequest(username, password);
    //   const response = await new ServerFacade().login(request);
    //   return [response.user, response.token];
    // } catch (e) {
    //   if((e as Error).message.includes("Error: Unauthorized")) throw new Error("Incorrect username or password");
    //   else throw e;
    // }
    return [FakeData.instance.fakeUsers[0], FakeData.instance.authToken]
  };

  public async register(firstName: string, lastName: string, username: string, password: string, userImageBytes: Uint8Array): Promise<[User, AuthToken]> {
    // let imageStringBase64: string = Buffer.from(userImageBytes).toString("base64");
    // const request = new RegisterRequest(firstName, lastName, username, password, imageStringBase64);
    // const response = await new ServerFacade().register(request);
    // return [response.user, response.token];
    return [FakeData.instance.fakeUsers[0], FakeData.instance.authToken]
  };

  public async getUser(authToken: AuthToken, username: string): Promise<User | null> {
    // const request = new AliasRequest(authToken, alias);
    // const response = await new ServerFacade().getUser(request);
    // return response.user;
    return FakeData.instance.findUserByUsername(username);
  };

  public async logout(authToken: AuthToken): Promise<void> {
    // const request = new AuthenticatedRequest(authToken);
    // await new ServerFacade().logout(request);
    await new Promise((resolve) => {setTimeout(resolve, 1000)});
  };
}