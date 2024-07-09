import {Card} from "../../../model/game/card/Card";
import {PlayingCardPresenter, PlayingCardView} from "./PlayingCardPresenter";


export class HandPlayingCardPresenter<T extends Card> extends PlayingCardPresenter<T> {
  private readonly _index: number;
  private readonly _onCardHover: (hovered: boolean) => void;
  private readonly _onCardSelected: () => void;
  private readonly _sizeUpdate: () => void;

  constructor(view: PlayingCardView, onCardHover: (hovered: boolean) => void, onCardSelect: () => void, sizeUpdate: () => void, index: number) {
    super(view, {zIndex: index, cursor: "pointer", position: "absolute", height: "100%", width: "auto"});
    this._onCardHover = onCardHover;
    this._sizeUpdate = sizeUpdate;
    this._onCardSelected = onCardSelect;
    this._index = index;
  }

  updatePos(xPos: number, focused: boolean) {
    this.addStyle({left: xPos, transform: focused ? "translateY(-10px)" : "none"});
  }



  mouseEnter() {
    this._onCardHover(true)
  }

  mouseLeave() {
    this._onCardHover(false);
  }

  mouseDown() {
    this._onCardSelected();
  }

  sizeUpdate(): void {
    super.sizeUpdate();
    this._sizeUpdate();
  }
}
