import {PlayingCardHandPresenter} from "../PlayingCardHandPresenter";
import {PokerCard} from "../../../../model/game/PokerCard";
import {GamePresenter, GameView} from "../../GamePresenter";
import {FakeData} from "../../../../fake/FakeData";
import {CardStackPresenter, CardStackView} from "../CardStackPresenter";
import {PlayingCardGroupView} from "../PlayingCardGroupPresenter";

export interface SevenCardRummyView extends GameView {
}

export class SevenCardRummyPresenter extends GamePresenter<SevenCardRummyView> {
  private userHand: PlayingCardHandPresenter<PokerCard> | null = null;
  private opponentHand: PlayingCardHandPresenter<PokerCard> | null = null;
  private drawPile: CardStackPresenter<PokerCard> | null = null;
  private discardPile: CardStackPresenter<PokerCard> | null = null;

  makeUserHandPresenter(view: PlayingCardGroupView): PlayingCardHandPresenter<PokerCard> {
    this.userHand = new PlayingCardHandPresenter(view, FakeData.instance.handOfCards, {position: "absolute", width: "100%", height: "15%", bottom: "5%"})
    return this.userHand;
  }

  makeOpponentHandPresenter(view: PlayingCardGroupView): PlayingCardHandPresenter<PokerCard> {
    this.opponentHand = new PlayingCardHandPresenter(view, FakeData.instance.oppCards, {position: "absolute", width: "100%", height: "15%"})
    return this.opponentHand;
  }

  makeDrawPile(view: CardStackView): CardStackPresenter<PokerCard> {
    this.drawPile = new CardStackPresenter(view);
    setTimeout(() => this.drawPile!.cards = [...FakeData.instance.oppCards, ...FakeData.instance.oppCards, ...FakeData.instance.oppCards], 100);
    return this.drawPile;
  }

  makeDiscardPile(view: CardStackView): CardStackPresenter<PokerCard> {
    this.discardPile = new CardStackPresenter(view);
    setTimeout(() => this.discardPile!.cards = [...FakeData.instance.handOfCards, ...FakeData.instance.handOfCards, ...FakeData.instance.handOfCards], 100);
    return this.discardPile;
  }

}