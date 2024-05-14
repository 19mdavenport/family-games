import {Presenter, View} from "./Presenter";
import React, {CSSProperties} from "react";
import {Size} from "../hooks/size/ElementSizeHook";

export interface StyleView extends View {
  setStyle(style: React.CSSProperties): void;
}

export abstract class StylePresenter<T extends StyleView> extends Presenter<T> {
  private _style: React.CSSProperties = {};
  private _viewSize: Size = {width: 0, height: 0};

  protected constructor(view: T, initialStyle?: CSSProperties) {
    super(view);
    this._style = initialStyle || {};
  }

  protected addStyle(style: React.CSSProperties) {
    this._style = {...this._style, ...style};
    this.setStyle()
  }

  protected removeStyle(style: React.CSSProperties) {
    Object.keys(style).forEach(key => delete (this._style as any)[key]);
    this.setStyle()
  }

  get viewSize(): Size {
    return this._viewSize;
  }

  set viewSize(value: Size) {
    this._viewSize = value;
  }

  sizeUpdate() {
    this.setStyle()
  }

  private setStyle() {
    this.view.setStyle(this._style);
  }
}