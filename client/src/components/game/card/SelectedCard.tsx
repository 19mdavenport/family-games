import "./PlayingCard.css"
import useElementSize from "../../../hooks/size/ElementSizeHook";
import React from "react";
import {Card} from "../../../model/game/Card";

interface Props<T extends Card> {
  card: T
  xPos: number
  yPos: number
  parentWidth: number
  total: number
  onMove: (newIndex: number, xPos: number, yPos: number) => void
  onDrop: () => void
}

const SelectedCard = <T extends Card>(props: Props<T>) => {
  const {ref, width} = useElementSize<HTMLImageElement>();
  const maxWidth = window.innerWidth * 2 / 3 > window.innerHeight ? props.parentWidth / 2 : window.innerWidth * 3 / 2 > window.innerHeight ? props.parentWidth * 3 / 4 : props.parentWidth;

  function calcNewIndex(xPos: number) {
    let offset = Math.abs(props.parentWidth - Math.min(props.parentWidth, maxWidth)) / 2;
    if (width * props.total > maxWidth) {
      return Math.floor((xPos - offset) * props.total / (maxWidth - width));
    } else return 0;
  }

  function handleMove(event: React.MouseEvent<HTMLImageElement>) {
    const xPos = props.xPos + event.movementX;
    props.onMove(calcNewIndex(xPos), xPos, props.yPos + event.movementY);
  }

  return (
    <>
      <img ref={ref}
           style={{zIndex: props.total, left: props.xPos, top: props.yPos}}
           className="playingCard" src={props.card.imageUrl()}
           alt={""}
           onMouseMove={handleMove}
           onMouseUp={props.onDrop}
           onDragStart={(e) => e.preventDefault()}
      />
    </>
  )
}
export default SelectedCard;