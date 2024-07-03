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

  updatePos(parentWidth: number, maxWidth: number, focused: number | null, total: number) {
    this.addStyle({
      left: Math.floor(this.calcXOffset(parentWidth, maxWidth, focused, total)),
      transform: focused === this._index ? "translateY(-10px)" : "none",
    });
  }

  private calcXOffset(parentWidth: number, maxWidth: number, focused: number | null, total: number) {
    let offset = (parentWidth - maxWidth) / 2;
    const width = this.viewSize.width;
    if (width * total > maxWidth) {
      if(focused != null && focused != total - 1) {
        focused >= this._index ? offset -= this._index : offset += total - this._index;
      }
      return (maxWidth - width) * this._index / (total - 1) + offset;
    }
    else return (maxWidth - width * total) / 2 + width * this._index + offset;
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
