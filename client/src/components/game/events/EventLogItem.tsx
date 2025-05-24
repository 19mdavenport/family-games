import React from "react";
import {GameEvent} from "../../../model/game/events/GameEvent";

interface Props {
  style: React.CSSProperties;
  event: GameEvent;
}

const EventLog = (props: Props) => {
  return (
    <div style={props.style}>
      <span style={{color: "rgba(255, 255, 255, 0.45)", fontSize: "smaller"}}>{new Date(props.event.timestamp).toLocaleTimeString()}</span> {props.event.text}
    </div>
  )
}
export default EventLog