import {PlayingCardPresenter, PlayingCardView} from "./PlayingCardPresenter";
import {Card} from "../../../model/game/card/Card";
import {CSSProperties} from "react";

export class CardStackTopPresenter<T extends Card> extends PlayingCardPresenter<T> {
  private readonly onClick: () => void;
  private readonly style: CSSProperties;

  constructor(view: PlayingCardView, style: CSSProperties, onClick: () => void) {
    super(view);
    this.style = style;
    this.onClick = onClick;
  }

  mouseDown() {
    this.onClick();
  }


  sizeUpdate() {
    super.sizeUpdate();
    this.addStyle(this.style)
  }
}