import {Card} from "./Card";

export enum Suit {
  HEARTS, DIAMONDS, CLUBS, SPADES, SPECIAL
}

export enum Rank {
  ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
  JACK, QUEEN, KING, JOKER, HIDDEN
}

export class PokerCard implements Card {
  private _suit: Suit;
  private _rank: Rank;

  constructor(suit: Suit, rank: Rank) {
    this._rank = rank;
    this._suit = suit;
  }

  get suit(): Suit {
    return this._suit;
  }

  get rank(): Rank {
    return this._rank;
  }

  imageUrl() {
    if (this._rank == Rank.JOKER) return "./cards/Joker.svg";
    if (this._rank == Rank.HIDDEN) return "./cards/Back.svg";
    let value;
    let suit;
    switch (this._rank) {
      case Rank.ACE:
        value = 'A';
        break;
      case Rank.TWO:
        value = '2';
        break;
      case Rank.THREE:
        value = '3';
        break;
      case Rank.FOUR:
        value = '4';
        break;
      case Rank.FIVE:
        value = '5';
        break;
      case Rank.SIX:
        value = '6';
        break;
      case Rank.SEVEN:
        value = '7';
        break;
      case Rank.EIGHT:
        value = '8';
        break;
      case Rank.NINE:
        value = '9';
        break;
      case Rank.TEN:
        value = 'T';
        break;
      case Rank.JACK:
        value = 'J';
        break;
      case Rank.QUEEN:
        value = 'Q';
        break;
      case Rank.KING:
        value = 'K';
        break;
    }
    switch (this._suit) {
      case Suit.SPADES:
        suit = 'S';
        break;
      case Suit.HEARTS:
        suit = 'H';
        break;
      case Suit.CLUBS:
        suit = 'C';
        break;
      case Suit.DIAMONDS:
        suit = 'D';
        break;
    }
    return `./cards/${value}${suit}.svg`;
  }

  keyString(): string {
    return `${Rank[this._rank]} of ${Suit[this._suit]}`;
  }
}