import {Card} from "../../../model/game/Card";
import {PlayingCardPresenter, PlayingCardView} from "./PlayingCardPresenter";
import {StylePresenter, StyleView} from "../../StylePresenter";
import React from "react";

export interface PlayingCardGroupView extends StyleView {
  setNumCards(numCards: number): void;
}

export class PlayingCardGroupPresenter<T extends Card, C extends PlayingCardPresenter<T> = PlayingCardPresenter<T>>
  extends StylePresenter<PlayingCardGroupView> {

  protected _cards: T[];
  private readonly _presenters: PlayingCardPresenter<T>[];

  constructor(view: PlayingCardGroupView, cards: T[], style: React.CSSProperties) {
    super(view);
    this._cards = cards;
    this._presenters = Array<C>(cards.length);
    this.addStyle(style);
  }

  makeChildPresenter(view: PlayingCardView, index: number) {
    const childPresenter = this.createChildPresenter(view, index);
    this._presenters[index] = childPresenter;
    return childPresenter;
  }

  createChildPresenter(view: PlayingCardView, index: number) {
    return new PlayingCardPresenter<T>(view, this._cards[index]);
  }

  updateAll() {
    for (let i = 0; i < this._presenters.length; i++) {
      if (this._presenters[i]) this.update(i);
    }
  }

  update(index: number) {
    this._presenters[index].card = this._cards[index];
  }

  sizeUpdate(): void {
    this.view.setNumCards(this._cards.length);
    this.updateAll();
  }

  protected getChildPresenter(index: number) {
    return this._presenters[index] as C;
  }

  set cards(cards: T[]) {
    this._cards = cards;
    this.updateAll();
  }
}