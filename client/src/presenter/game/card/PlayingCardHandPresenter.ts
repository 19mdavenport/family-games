import {Card} from "../../../model/game/card/Card";
import {HandPlayingCardPresenter} from "./HandPlayingCardPresenter";
import {PlayingCardView} from "./PlayingCardPresenter";
import React from "react";
import {PlayingCardGroupPresenter, PlayingCardGroupView} from "./PlayingCardGroupPresenter";

export class PlayingCardHandPresenter<T extends Card> extends PlayingCardGroupPresenter<T, HandPlayingCardPresenter<T>> {
  _focused: number | null = null;
  private _locked: boolean = false;
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
    this.getChildPresenter(index).updatePos(this.calcXOffset(index, this._focused, this._cards.length), this._focused == index);
    super.update(index);
  }

  private calcXOffset(index: number, focused: number | null, total: number) {
    let maxWidth;
    const width = this.viewSize.width
    if (window.innerWidth * 2 / 3 > window.innerHeight) maxWidth = width / 2;
    else if (window.innerWidth * 3 / 2 > window.innerHeight) maxWidth = width * 3 / 4
    else maxWidth = width;
    let offset = (width - maxWidth) / 2;
    const childWidth = this.getChildPresenter(index).viewSize.width;
    if (childWidth * total > maxWidth) {
      if(focused != null && focused != total - 1) {
        focused >= index ? offset -= index : offset += total - index;
      }
      return (maxWidth - childWidth) * index / (total - 1) + offset;
    }
    else return (maxWidth - childWidth * total) / 2 + childWidth * index + offset;
  }

  setFocus(focus: number | null) {
    if(!this._locked) {
      this._focused = focus;
      this.updateAll();
    }
  }

  set locked(locked: boolean) {
    this._locked = locked;
    if(!locked) {
      this.getChildPresenter(this._focused!).showTentative(false);
      this._focused = null;
      this.updateAll();
    } else {
      this.getChildPresenter(this._focused!).showTentative(true);
    }
  }

  shiftLock(xPos: number) {
    if(this._locked) {
      const firstChild = this.getChildPresenter(0);
      if (firstChild) xPos -= firstChild.viewSize.width / 2;
      const newIndex = this._cards.map((_card: Card, index: number) => {
        return {index: index, xPos: xPos - this.calcXOffset(index, null, this._cards.length)}
      }).toSorted((a, b) => Math.abs(a.xPos) - Math.abs(b.xPos))[0].index;
      this._cards[newIndex] = this._cards.splice(this._focused!, 1, this._cards[newIndex])[0];
      this.getChildPresenter(this._focused!).showTentative(false);
      this.getChildPresenter(newIndex).showTentative(true);
      this._focused = newIndex;
      this.updateAll();
    }
  }

}