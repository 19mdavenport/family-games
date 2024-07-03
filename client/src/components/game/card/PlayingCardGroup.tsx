import PlayingCard from "./PlayingCard";
import React, {ReactElement, useEffect, useState} from "react";
import useElementSize from "../../../hooks/size/ElementSizeHook";
import {Card} from "../../../model/game/card/Card";
import usePresenter, {PresenterProps} from "../../../hooks/presenter/PresenterHook";
import {PlayingCardGroupPresenter, PlayingCardGroupView} from "../../../presenter/game/card/PlayingCardGroupPresenter";

const PlayingCardGroup = <T extends Card>(props: PresenterProps<PlayingCardGroupView, PlayingCardGroupPresenter<T>>) => {
  const {ref, size} = useElementSize<HTMLDivElement>();
  const [numCards, setNumCards] = useState<number>(0);
  const [cardElements, setCardElements] = useState<ReactElement[]>([]);
  const [style, setStyle] = useState<React.CSSProperties>({});

  const presenter = usePresenter(props, {setNumCards, setStyle});

  useEffect(() => {
    if (cardElements.length < numCards) {
      const next = [];
      while (cardElements.length + next.length < numCards) {
        const i = cardElements.length + next.length;
        next.push(<PlayingCard key={i.toString()} presenterGenerator={(view) => presenter!.makeChildPresenter(view, i)}/>);
      }
      setCardElements([...cardElements, ...next]);
    }
  }, [numCards]);

  useEffect(() => {presenter && (presenter.viewSize = size) && presenter.sizeUpdate()}, [size, presenter]);

  return (
    <div ref={ref} style={style}>
      {cardElements.slice(0, numCards)}
    </div>
  )
}

export default PlayingCardGroup;


// const PlayingCardGroup = <T extends PlayingCard>(props: Props<T>) => {
//   const {ref, width} = useElementSize<HTMLDivElement>();
//   const [focused, setFocused] = useState<number | null>(null);
//   const [selected, setSelected] = useState<[T, number, number, number] | null>(null);
//   const [cards, setCards] = useState<T[]>([]);
//
//   function makeCards() {
//     let cardElements: ReactElement[] = [];
//     cards.forEach((card, i) => {
//       cardElements.push(<PlayingCard key={`${card.keyString()} ${i}`}
//                                              card={card}
//                                              index={i}
//                                              total={cards.length}
//                                              focused={focused}
//                                              parentWidth={width}
//                                              onHover={() => setFocused(i)}
//                                              onMouseLeave={() => focused === i && setFocused(null)}
//                                              onMouseDown={(xPos, yPos) => {
//                                                setSelected([cards[i], i, xPos, yPos])
//                                                setFocused(i)
//                                              }}
//       />)
//     });
//     return cardElements;
//   }
//
//   return (
//     <div ref={ref} className="userHand">
//       {makeCards()}
//       {selected && <SelectedCard card={selected[0]}
//                                  parentWidth={width}
//                                  total={cards.length}
//                                  xPos={selected[2]}
//                                  yPos={selected[3]}
//                                  onMove={(newIndex, xPos, yPos) => {
//                                    const oldIndex = Math.max(0, Math.min(cards.length - 1, selected[1]));
//                                    newIndex = Math.max(0, Math.min(cards.length - 1, newIndex));
//                                    setCards(cards.toSpliced(oldIndex, 1).toSpliced(newIndex, 0, selected[0]));
//                                    setFocused(newIndex);
//                                    setSelected([selected[0], newIndex, xPos, yPos]);
//                                  }}
//                                  onDrop={() => {
//                                    setFocused(null);
//                                    setSelected(null);
//                                  }}
//       />}
//     </div>
//   )
// }