import {PlayingCardPresenter, PlayingCardView} from "./PlayingCardPresenter";
import {Card} from "../../../model/game/card/Card";
import {CSSProperties} from "react";

export class SelectedCardPresenter<T extends Card> extends PlayingCardPresenter<T>{
  private readonly _sizeUpdate: () => void;

  constructor(view: PlayingCardView, mouseUp: () => void, sizeUpdate: () => void, style?: CSSProperties, initialCard?: T) {
    super(view, style, initialCard);
    this.mouseUp = mouseUp;
    this._sizeUpdate = sizeUpdate;
  }


  sizeUpdate() {
    super.sizeUpdate();
    this._sizeUpdate();
  }
}