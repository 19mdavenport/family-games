import {PokerCard} from "../PokerCard";

export default class SevenCardRummyGame {
  private readonly _hand: PokerCard[];
  private readonly _oppHand: PokerCard[];
  private readonly _drawStack: PokerCard[];
  private readonly _discards: PokerCard[];

  constructor(hand: PokerCard[], oppHand: PokerCard[], drawStack: PokerCard[], discards: PokerCard[]) {
    this._hand = hand;
    this._oppHand = oppHand;
    this._drawStack = drawStack;
    this._discards = discards;
  }

  get hand(): PokerCard[] {
    return this._hand;
  }

  get oppHand(): PokerCard[] {
    return this._oppHand;
  }

  get drawStack(): PokerCard[] {
    return this._drawStack;
  }

  get discards(): PokerCard[] {
    return this._discards;
  }
}