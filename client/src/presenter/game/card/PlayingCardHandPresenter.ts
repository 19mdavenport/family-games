import {Card} from "../../../model/game/card/Card";
import {HandPlayingCardPresenter} from "./HandPlayingCardPresenter";
import {PlayingCardView} from "./PlayingCardPresenter";
import React from "react";
import {PlayingCardGroupPresenter, PlayingCardGroupView} from "./PlayingCardGroupPresenter";

export class PlayingCardHandPresenter<T extends Card> extends PlayingCardGroupPresenter<T, HandPlayingCardPresenter<T>> {
  private _focused: number | null = null;
  private readonly _onCardSelected: (index: number) => void;

  constructor(view: PlayingCardGroupView, onCardSelected: (index: number) => void, cards: T[], style: React.CSSProperties) {
    super(view, cards, style);
    this._onCardSelected = onCardSelected;
  }

  createChildPresenter(view: PlayingCardView, index: number) {
    return new HandPlayingCardPresenter<T>(view,
      (hovered: boolean) => this.setFocus(hovered ? index : null),
      () => this._onCardSelected(index),
      () => this.update(index),
      index);
  }

  update(index: number) {
    let maxWidth;
    const width = this.viewSize.width
    if (window.innerWidth * 2 / 3 > window.innerHeight) maxWidth = width / 2;
    else if (window.innerWidth * 3 / 2 > window.innerHeight) maxWidth = width * 3 / 4
    else maxWidth = width;
    this.getChildPresenter(index).updatePos(width, maxWidth, this._focused, this._cards.length);
    super.update(index);
  }

  setFocus(focus: number | null) {
    this._focused = focus;
    this.updateAll();
  }

}