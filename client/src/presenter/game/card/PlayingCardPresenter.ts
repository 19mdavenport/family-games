import {Card} from "../../../model/game/Card";
import {StylePresenter, StyleView} from "../../StylePresenter";
import React from "react";

export interface PlayingCardView extends StyleView {
  setImageUrl(imageUrl: string): void;
}

export class PlayingCardPresenter<T extends Card> extends StylePresenter<PlayingCardView> {
  private readonly initialCard: T | undefined;

  constructor(view: PlayingCardView, initialCard?: T) {
    super(view);
    this.initialCard = initialCard;
  }

  set card(value: T) {
    this.view.setImageUrl(value.imageUrl());
  }

  mouseEnter(): void {}
  mouseLeave(): void {}
  mouseDown(): void {}
  mouseMove(event: React.MouseEvent<HTMLElement>): void {}
  mouseUp(): void {}
  sizeUpdate(): void {
    if(this.initialCard) this.card = this.initialCard;
  }
}