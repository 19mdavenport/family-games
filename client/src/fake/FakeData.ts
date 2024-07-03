import AuthToken from "../model/domain/AuthToken";
import User from "../model/domain/User";
import {PokerCard, Rank, Suit} from "../model/game/card/PokerCard";

const MALE_IMAGE_URL: string =
  "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
const FEMALE_IMAGE_URL: string =
  "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

export class FakeData {
  private readonly _authToken: AuthToken = AuthToken.Generate();

  public get authToken() {
    return this._authToken;
  }

  private readonly allUsers: User[] = [
    new User("Allen", "Anderson", "@allen", MALE_IMAGE_URL),
    new User("Amy", "Ames", "@amy", FEMALE_IMAGE_URL),
    new User("Bob", "Bobson", "@bob", MALE_IMAGE_URL),
    new User("Bonnie", "Beatty", "@bonnie", FEMALE_IMAGE_URL),
    new User("Chris", "Colston", "@chris", MALE_IMAGE_URL),
    new User("Cindy", "Coats", "@cindy", FEMALE_IMAGE_URL),
    new User("Dan", "Donaldson", "@dan", MALE_IMAGE_URL),
    new User("Dee", "Dempsey", "@dee", FEMALE_IMAGE_URL),
    new User("Elliott", "Enderson", "@elliott", MALE_IMAGE_URL),
    new User("Elizabeth", "Engle", "@elizabeth", FEMALE_IMAGE_URL),
    new User("Frank", "Frandson", "@frank", MALE_IMAGE_URL),
    new User("Fran", "Franklin", "@fran", FEMALE_IMAGE_URL),
    new User("Gary", "Gilbert", "@gary", MALE_IMAGE_URL),
    new User("Giovanna", "Giles", "@giovanna", FEMALE_IMAGE_URL),
    new User("Henry", "Henderson", "@henry", MALE_IMAGE_URL),
    new User("Helen", "Hopwell", "@helen", FEMALE_IMAGE_URL),
    new User("Igor", "Isaacson", "@igor", MALE_IMAGE_URL),
    new User("Isabel", "Isaacson", "@isabel", FEMALE_IMAGE_URL),
    new User("Justin", "Jones", "@justin", MALE_IMAGE_URL),
    new User("Jill", "Johnson", "@jill", FEMALE_IMAGE_URL),
    new User("Kent", "Knudson", "@kent", MALE_IMAGE_URL),
    new User("Kathy", "Kunzler", "@kathy", FEMALE_IMAGE_URL),
  ];

  private _handOfCards: PokerCard[] = []
  private _oppCards: PokerCard[] = []

  public get fakeUsers(): User[] {
    return this.allUsers;
  }

  public get handOfCards() {
    return this._handOfCards;
  }

  public get oppCards() {
    return this._oppCards;
  }

  private static _instance: FakeData;

  /**
   * Returns the singleton instance.
   */
  public static get instance(): FakeData {
    if (FakeData._instance == null) {
      FakeData._instance = new FakeData();
    }

    return this._instance;
  }

  private constructor() {
    // eslint-disable-next-line no-self-compare
    if (this.fakeUsers !== this.fakeUsers) {
      // Verify that this.fakeUsers always returns the same list of users (this could be violated by mock implementations of fakeUsers)
      throw new Error(
        "fakeUsers should return the same list of fake users each time it's called",
      );
    }

    for(let i = 0; i < 7; i++) {
      const suit: Suit = Math.floor(Math.random() * 4);
      const rank: Rank = Math.floor(Math.random() * 13);
      this._handOfCards.push(new PokerCard(suit, rank));
      this._oppCards.push(new PokerCard(Suit.SPECIAL, Rank.HIDDEN));
    }

  }


  /**
   * Finds the user with the specified username.
   *
   * @param username the username of the user to be returned.
   * @returns the user or null if no user is found with the specified username.
   */
  public findUserByUsername(username: string): User | null {
    for (let user of this.fakeUsers) {
      if (user.username === username) {
        return user;
      }
    }

    return null;
  }

}
