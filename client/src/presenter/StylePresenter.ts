import {Presenter, View} from "./Presenter";
import React from "react";
import {Size} from "../hooks/size/ElementSizeHook";

export interface StyleView extends View {
  setStyle(style: React.CSSProperties): void;
}

export abstract class StylePresenter<T extends StyleView> extends Presenter<T> {
  private _style: React.CSSProperties = {};
  private _viewSize: Size = {width: 0, height: 0};

  protected addStyle(style: React.CSSProperties) {
    this._style = {...this._style, ...style};
    this.view.setStyle(this._style);
  }

  protected removeStyle(style: React.CSSProperties) {
    Object.keys(style).forEach(key => delete (this._style as any)[key]);
    this.view.setStyle(this._style);
  }

  get viewSize(): Size {
    return this._viewSize;
  }

  set viewSize(value: Size) {
    this._viewSize = value;
  }

  abstract sizeUpdate(): void
}