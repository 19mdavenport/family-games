import {Presenter, View} from "../../Presenter";
import {CardStackTopPresenter} from "./CardStackTopPresenter";
import {Card} from "../../../model/game/Card";
import { PlayingCardView } from "./PlayingCardPresenter";
import {PlayingCardGroupPresenter, PlayingCardGroupView} from "./PlayingCardGroupPresenter";
import {CSSProperties} from "react";

export interface CardStackView extends View {
  setOpen: (open: boolean) => void;
}

export class CardStackPresenter<T extends Card> extends Presenter<CardStackView> {
  private topCardPresenter: CardStackTopPresenter<T> | null = null;
  private groupPresenter: PlayingCardGroupPresenter<T> | null = null;
  private _cards: T[] = [];
  private readonly style: CSSProperties;

  constructor(view: CardStackView, style: CSSProperties) {
    super(view);
    this.style = style;
  }

  makeTopCardPresenter(view: PlayingCardView) {
    this.topCardPresenter = new CardStackTopPresenter(view, this.style, () => this.topClicked());
    return this.topCardPresenter;
  }

  makeGroupPresenter(view: PlayingCardGroupView) {
    this.groupPresenter = new PlayingCardGroupPresenter<T>(view, this._cards, {height: "15%", width: "auto"});
    return this.groupPresenter;
  }

  private topClicked(): void {
    this.view.setOpen(true);
  };

  popUpClosed() {
    this.view.setOpen(false);
  }

  set cards(cards: T[]) {
    if(this.topCardPresenter && cards.length > 0) this.topCardPresenter.card = cards[0];
    if(this.groupPresenter) this.groupPresenter.cards = cards;
    this._cards = cards;
  }
}