import {Presenter, View} from "../../Presenter";
import {CardStackTopPresenter} from "./CardStackTopPresenter";
import {Card} from "../../../model/game/card/Card";
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
  private topCard: T | null = null;
  private readonly topCardStyle: CSSProperties;

  constructor(view: CardStackView, topCardStyle: CSSProperties) {
    super(view);
    this.topCardStyle = {cursor: "pointer", ...topCardStyle};
  }

  makeTopCardPresenter(view: PlayingCardView) {
    this.topCardPresenter = new CardStackTopPresenter(view, this.topCardStyle, () => this.topClicked());
    if(this.topCard) this.topCardPresenter.card = this.topCard;
    return this.topCardPresenter;
  }

  makeGroupPresenter(view: PlayingCardGroupView) {
    const heightPx = `${this.topCardPresenter!.viewSize.height}px`;
    this.groupPresenter = new PlayingCardGroupPresenter<T>(view, this._cards,
      {display: "flex", height: heightPx},
      {height: heightPx, width: "auto"});
    return this.groupPresenter;
  }

  private topClicked(): void {
    this.view.setOpen(true);
  };

  popUpClosed() {
    this.view.setOpen(false);
  }

  set cards(cards: T[]) {
    if(cards.length > 0) {
      this.topCard = cards[0];
      if(this.topCardPresenter) this.topCardPresenter.card = this.topCard;
    }
    if(this.groupPresenter) this.groupPresenter.cards = cards;
    this._cards = cards;
  }

  addTentativeCard(card: T) {
    this.topCard = card;
    if(this.topCardPresenter) {
      this.topCardPresenter.card = card;
      this.topCardPresenter.showTentative(true);
    }
  }

  removeTentativeCard(keepCard: boolean) {
    if(this.topCardPresenter) {
      if (keepCard) {
        this._cards.splice(0, 0, this.topCard!);
        if (this.groupPresenter) this.groupPresenter.cards = this._cards;
      } else {
        this.topCardPresenter.card = this._cards[0];
      }
      this.topCardPresenter.showTentative(false);
    }
  }

}