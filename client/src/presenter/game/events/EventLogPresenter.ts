import {Presenter} from "../../Presenter";
import {GameEvent} from "../../../model/game/events/GameEvent";
import {FakeData} from "../../../fake/FakeData";

export interface EventLogView {
  setItems: (items: GameEvent[]) => void;
}

export class EventLogPresenter extends Presenter<EventLogView> {
  constructor(view: EventLogView) {
    super(view);
    setTimeout(() => view.setItems(FakeData.instance.events), 10);
  }

}