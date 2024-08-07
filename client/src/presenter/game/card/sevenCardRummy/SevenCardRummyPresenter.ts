import {PlayingCardHandPresenter} from "../PlayingCardHandPresenter";
import {PokerCard} from "../../../../model/game/card/PokerCard";
import {GamePresenter, GameView} from "../../GamePresenter";
import {FakeData} from "../../../../fake/FakeData";
import {CardStackPresenter, CardStackView} from "../CardStackPresenter";
import {PlayingCardGroupView} from "../PlayingCardGroupPresenter";
import {SelectedCardPresenter} from "../SelectedCardPresenter";
import {PlayingCardView} from "../PlayingCardPresenter";
import SevenCardRummyGame from "../../../../model/game/card/sevenCardRummy/SevenCardRummyGame";

export interface SevenCardRummyView extends GameView {
  setCardSelected: (selected: boolean) => void;
}

export class SevenCardRummyPresenter extends GamePresenter<SevenCardRummyView> {
  private game: SevenCardRummyGame = new SevenCardRummyGame(FakeData.instance.handOfCards, FakeData.instance.oppCards, [...FakeData.instance.handOfCards, ...FakeData.instance.handOfCards, ...FakeData.instance.handOfCards], [...FakeData.instance.oppCards, ...FakeData.instance.oppCards, ...FakeData.instance.oppCards]);
  private userHand: PlayingCardHandPresenter<PokerCard> | null = null;
  private opponentHand: PlayingCardHandPresenter<PokerCard> | null = null;
  private drawPile: CardStackPresenter<PokerCard> | null = null;
  private discardPile: CardStackPresenter<PokerCard> | null = null;
  private selectedCard: SelectedCardPresenter<PokerCard> | null = null;
  private card: PokerCard | null = null;
  private mousePos: {x: number, y: number} = {x: 0, y: 0};

  constructor(view: SevenCardRummyView) {
    super(view);
    window.addEventListener("mousemove", (event) => {
      this.mousePos = {x: event.clientX, y: event.clientY};
      this.updateCardPos()
    })
  }

  makeUserHandPresenter(view: PlayingCardGroupView): PlayingCardHandPresenter<PokerCard> {
    this.userHand = new PlayingCardHandPresenter(view, (index) => this.selectCard(index), this.game.hand, {position: "absolute", width: "100%", height: "15%", bottom: "5%"})
    return this.userHand;
  }

  makeOpponentHandPresenter(view: PlayingCardGroupView): PlayingCardHandPresenter<PokerCard> {
    this.opponentHand = new PlayingCardHandPresenter(view, () => {}, this.game.oppHand, {position: "absolute", width: "100%", height: "15%", top: "14%"})
    return this.opponentHand;
  }

  makeDrawPile(view: CardStackView): CardStackPresenter<PokerCard> {
    this.drawPile = new CardStackPresenter(view, {position: "absolute", height: "15%", width: "auto", left: "53%", top: "47%"});
    this.drawPile!.cards = this.game.drawStack;
    return this.drawPile;
  }

  makeDiscardPile(view: CardStackView): CardStackPresenter<PokerCard> {
    this.discardPile = new CardStackPresenter(view, {position: "absolute", height: "15%", width: "auto", right: "53%", top: "47%"});
    this.discardPile!.cards = this.game.discards;
    return this.discardPile;
  }

  makeSelectedCard(view: PlayingCardView): SelectedCardPresenter<PokerCard> {
    const height = this.userHand ? `${this.userHand.viewSize.height}px` : "20%";
    this.selectedCard = new SelectedCardPresenter<PokerCard>(view, () => this.mouseUp(), () => this.updateCardPos(), {position: "absolute", zIndex: 10, height: height, top: -100, left: -100}, this.card || undefined);
    return this.selectedCard;
  }

  updateCardPos() {
    if(this.selectedCard) {
      const width = this.selectedCard.viewSize.width;
      const height = this.selectedCard.viewSize.height;
      const x = Math.max(Math.min(this.mousePos.x - width / 2, window.innerWidth - width), 0);
      const y = Math.max(Math.min(this.mousePos.y - height / 2, window.innerHeight - height), 0);
      if(width > 0 && height > 0) {
        this.selectedCard.addStyle({left: x, top: y, display: "unset"});
      }
    }
  }

  mouseUp() {
    // debugger;
    this.view.setCardSelected(false);
  }

  selectCard(index: number) {
    this.card = this.game.hand[index];
    if(this.selectedCard) {
      this.selectedCard.card = this.card;
    }
    this.view.setCardSelected(true);
  }

}