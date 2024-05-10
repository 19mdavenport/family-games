import React from "react";
import PopUp from "../../popup/PopUp";
import {CardStackPresenter, CardStackView} from "../../../presenter/game/card/CardStackPresenter";
import {Card} from "../../../model/game/Card";
import PlayingCard from "./PlayingCard";
import usePresenter, {PresenterProps} from "../../../hooks/presenter/PresenterHook";
import PlayingCardGroup from "./PlayingCardGroup";

const CardStack = <T extends Card>(props: PresenterProps<CardStackView, CardStackPresenter<T>>) => {
  const [open, setOpen] = React.useState(false);

  const presenter = usePresenter(props, {setOpen});

  if(!presenter) return (<></>);
  const pres = presenter as CardStackPresenter<T>;
  return (
    <div style={{position: "absolute", height: "15%"}}>
      <PlayingCard presenterGenerator={(view) => pres.makeTopCardPresenter(view)}/>
      {open && <PopUp containerStyle={{position: "absolute", height: "15%"}} onClose={() => pres.popUpClosed()}>
        <PlayingCardGroup presenterGenerator={(view) => pres.makeGroupPresenter(view)}/>
      </PopUp>}
    </div>
  )
}
export default CardStack