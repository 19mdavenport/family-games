export default class User {
  private _firstName: string;
  private _lastName: string;
  private _username: string;
  private _imageUrl: string;

  public constructor(
    firstName: string,
    lastName: string,
    alias: string,
    imageUrl: string
  ) {
    this._firstName = firstName;
    this._lastName = lastName;
    this._username = alias;
    this._imageUrl = imageUrl;
  }

  public get firstName(): string {
    return this._firstName;
  }

  public get lastName(): string {
    return this._lastName;
  }

  public get name() {
    return `${this.firstName} ${this.lastName}`;
  }

  public get username(): string {
    return this._username;
  }

  public get imageUrl(): string {
    return this._imageUrl;
  }

  public static fromJson(json: string | null | undefined): User | null {
    if (!!json) {
      let jsonObject: {
        _firstName: string;
        _lastName: string;
        _username: string;
        _imageUrl: string;
      } = JSON.parse(json);
      return new User(
        jsonObject._firstName,
        jsonObject._lastName,
        jsonObject._username,
        jsonObject._imageUrl
      );
    } else {
      return null;
    }
  }

  public toJson(): string {
    return JSON.stringify(this);
  }
}
