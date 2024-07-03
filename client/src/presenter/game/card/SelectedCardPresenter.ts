import {PlayingCardPresenter, PlayingCardView} from "./PlayingCardPresenter";
import {Card} from "../../../model/game/card/Card";
import React, {CSSProperties} from "react";

export class SelectedCardPresenter<T extends Card> extends PlayingCardPresenter<T>{

  constructor(view: PlayingCardView, mouseUp: () => void, style?: CSSProperties, initialCard?: T) {
    super(view, style, initialCard);
    this.mouseUp = mouseUp;
  }

}