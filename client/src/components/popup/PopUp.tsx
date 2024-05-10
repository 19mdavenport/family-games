import "./PopUp.css"
import React from "react";

interface Props {
  children: React.ReactElement[] | React.ReactElement | null;
  containerStyle?: React.CSSProperties;
  onClose(): void;
}

const PopUp = (props: Props) => {
  return (
    <div className="popUpOverlay" onClick={props.onClose}>
      <div className="popUpContainer bg-dark" style={props.containerStyle}>
        <span onClick={props.onClose} className="closeButton">x</span>
        {props.children}
      </div>
    </div>
  )
}
export default PopUp