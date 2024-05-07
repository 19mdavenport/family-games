import "./PlayingCard.css"
import useElementSize from "../../../hooks/size/ElementSizeHook";
import {Card} from "../../../model/game/Card";
import {PlayingCardPresenter, PlayingCardView} from "../../../presenter/game/card/PlayingCardPresenter";
import React, {useEffect, useState} from "react";
import usePresenter, {PresenterProps} from "../../../hooks/presenter/PresenterHook";

const PlayingCard = <T extends Card>(props: PresenterProps<PlayingCardView, PlayingCardPresenter<T>>) => {
  const {ref, size} = useElementSize<HTMLImageElement>();
  const [imageUrl, setImageUrl] = useState<string>("");
  const [style, setStyle] = useState<React.CSSProperties>({});

  const presenter = usePresenter(props, {setImageUrl, setStyle});
  useEffect(() => {presenter && (presenter.viewSize = size) && presenter.sizeUpdate()}, [size, presenter]);

  return (
      <img ref={ref}
         style={style}
         className="playingCard" src={imageUrl}
         alt={""}
         onMouseEnter={() => presenter!.mouseEnter()}
         onMouseLeave={() => presenter!.mouseLeave()}
         onMouseDown={() => presenter!.mouseDown()}
         onMouseMove={(event) => presenter!.mouseMove(event)}
         onMouseUp={() => presenter!.mouseUp()}
         onDragStart={(e) => e.preventDefault()}
      />
  )
}

export default PlayingCard;