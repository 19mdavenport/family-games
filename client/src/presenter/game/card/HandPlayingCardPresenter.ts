import {Card} from "../../../model/game/Card";
import {PlayingCardPresenter, PlayingCardView} from "./PlayingCardPresenter";


export class HandPlayingCardPresenter<T extends Card> extends PlayingCardPresenter<T> {
  private readonly _index: number;
  private _onCardHover: (hovered: boolean) => void;
  private _sizeUpdate: () => void;

  constructor(view: PlayingCardView, onCardHover: (hovered: boolean) => void, sizeUpdate: () => void, index: number) {
    super(view);
    this._onCardHover = onCardHover;
    this._sizeUpdate = sizeUpdate;
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

  }

  sizeUpdate(): void {
    this.addStyle({zIndex: this._index, position: "absolute", height: "100%", width: "auto", cursor: "pointer"});
    this._sizeUpdate();
  }
}
