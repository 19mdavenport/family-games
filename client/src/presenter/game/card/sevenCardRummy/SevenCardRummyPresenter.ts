import {PlayingCardHandPresenter} from "../PlayingCardHandPresenter";
import {PokerCard, Rank, Suit} from "../../../../model/game/PokerCard";
import {GamePresenter, GameView} from "../../GamePresenter";
import {FakeData} from "../../../../fake/FakeData";
import {CardStackPresenter, CardStackView} from "../CardStackPresenter";
import {PlayingCardGroupView} from "../PlayingCardGroupPresenter";
import {SelectedCardPresenter} from "../SelectedCardPresenter";
import {PlayingCardView} from "../PlayingCardPresenter";

export interface SevenCardRummyView extends GameView {

}

export class SevenCardRummyPresenter extends GamePresenter<SevenCardRummyView> {
  private userHand: PlayingCardHandPresenter<PokerCard> | null = null;
  private opponentHand: PlayingCardHandPresenter<PokerCard> | null = null;
  private drawPile: CardStackPresenter<PokerCard> | null = null;
  private discardPile: CardStackPresenter<PokerCard> | null = null;
  private selectedCard: SelectedCardPresenter<PokerCard> | null = null;

  makeUserHandPresenter(view: PlayingCardGroupView): PlayingCardHandPresenter<PokerCard> {
    this.userHand = new PlayingCardHandPresenter(view, (index) => this.selectCard(index), FakeData.instance.handOfCards, {position: "absolute", width: "100%", height: "15%", bottom: "5%"})
    return this.userHand;
  }

  makeOpponentHandPresenter(view: PlayingCardGroupView): PlayingCardHandPresenter<PokerCard> {
    this.opponentHand = new PlayingCardHandPresenter(view, () => {}, FakeData.instance.oppCards, {position: "absolute", width: "100%", height: "15%"})
    return this.opponentHand;
  }

  makeDrawPile(view: CardStackView): CardStackPresenter<PokerCard> {
    this.drawPile = new CardStackPresenter(view, {position: "absolute", height: "15%", width: "auto", right: "53%", top: "47%"});
    setTimeout(() => this.drawPile!.cards = [...FakeData.instance.oppCards, ...FakeData.instance.oppCards, ...FakeData.instance.oppCards], 100);
    return this.drawPile;
  }

  makeDiscardPile(view: CardStackView): CardStackPresenter<PokerCard> {
    this.discardPile = new CardStackPresenter(view, {position: "absolute", height: "15%", width: "auto", left: "53%", top: "47%"});
    setTimeout(() => this.discardPile!.cards = [...FakeData.instance.handOfCards, ...FakeData.instance.handOfCards, ...FakeData.instance.handOfCards], 100);
    return this.discardPile;
  }

  makeSelectedCard(view: PlayingCardView): SelectedCardPresenter<PokerCard> {
    this.selectedCard = new SelectedCardPresenter<PokerCard>(view, () => this.mouseUp(), {position: "absolute", zIndex: 10}, new PokerCard(Suit.CLUBS, Rank.FOUR))
    window.addEventListener("mousemove", (event) => this.mouseMove(event))
    return this.selectedCard;
  }

  mouseMove(event: MouseEvent) {
    let newHeight = "20%";
    if(this.userHand) newHeight = `${this.userHand.viewSize.height}px`;
    if(this.selectedCard) {
      const width = this.selectedCard.viewSize.width;
      const height = this.selectedCard.viewSize.height;
      const x = Math.max(Math.min(event.clientX - width / 2, window.innerWidth - width), 0);
      const y = Math.max(Math.min(event.clientY - height / 2, window.innerHeight - height), 0);
      this.selectedCard.addStyle({left: x, top: y, height: newHeight});
    }
  }

  mouseUp() {
    debugger;
  }

  selectCard(index: number) {

  }

}