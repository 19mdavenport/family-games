import {PlayingCardPresenter, PlayingCardView} from "./PlayingCardPresenter";
import {Card} from "../../../model/game/Card";

export class CardStackTopPresenter<T extends Card> extends PlayingCardPresenter<T> {
  private readonly onClick: () => void;

  constructor(view: PlayingCardView, onClick: () => void) {
    super(view);
    this.onClick = onClick;
  }

  mouseDown() {
    this.onClick();
  }
}