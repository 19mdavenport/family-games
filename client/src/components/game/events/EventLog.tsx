import React, {ReactElement, useState} from "react";
import {PresenterProps} from "../../../hooks/presenter/PresenterHook";
import {GameEvent} from "../../../model/game/events/GameEvent";
import EventLogItem from "./EventLogItem";
import {EventLogPresenter, EventLogView} from "../../../presenter/game/events/EventLogPresenter";

interface Props extends PresenterProps<EventLogView, EventLogPresenter> {
  style: React.CSSProperties;
  itemStyle: React.CSSProperties;
}

const EventLog = (props: Props) => {
  const [items, setItems] = useState<GameEvent[]>([]);
  const [presenter] = useState(props.presenterGenerator({setItems}));

  function createList(): ReactElement[] {
    const list = []
    for (let i = 0; i < items.length; i++) {
      list.push(
        // <li key={i}>
          <EventLogItem key={i} style={props.itemStyle} event={items[i]}/>
        // </li>
      );
    }
    // return (<ul>{list}</ul>);
    return list;
  }

  return (
    <div style={props.style}>
      {createList()}
    </div>
  )
}
export default EventLog