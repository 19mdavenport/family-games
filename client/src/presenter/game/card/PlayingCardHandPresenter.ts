import {Card} from "../../../model/game/Card";
import {HandPlayingCardPresenter} from "./HandPlayingCardPresenter";
import {PlayingCardView} from "./PlayingCardPresenter";
import React from "react";
import {PlayingCardGroupPresenter, PlayingCardGroupView} from "./PlayingCardGroupPresenter";

export class PlayingCardHandPresenter<T extends Card> extends PlayingCardGroupPresenter<T, HandPlayingCardPresenter<T>> {
  private _focused: number | null = null;

  constructor(view: PlayingCardGroupView, cards: T[], style: React.CSSProperties) {
    super(view, cards, style);
  }

  createChildPresenter(view: PlayingCardView, index: number) {
    return new HandPlayingCardPresenter<T>(view,
      (hovered: boolean) => this.setFocus(hovered ? index : null),
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