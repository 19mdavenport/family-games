import React, {useState} from "react";
import PopUp from "../../popup/PopUp";
import {CardStackPresenter, CardStackView} from "../../../presenter/game/card/CardStackPresenter";
import {Card} from "../../../model/game/card/Card";
import PlayingCard from "./PlayingCard";
import {PresenterProps} from "../../../hooks/presenter/PresenterHook";
import PlayingCardGroup from "./PlayingCardGroup";

const CardStack = <T extends Card>(props: PresenterProps<CardStackView, CardStackPresenter<T>>) => {
  const [open, setOpen] = React.useState(false);

  const [presenter] = useState(props.presenterGenerator({setOpen}));

  // if(!presenter) return (<></>);
  // const pres = presenter as CardStackPresenter<T>;
  return (
    <div>
      <PlayingCard presenterGenerator={(view) => presenter.makeTopCardPresenter(view)}/>
      {open && <PopUp onClose={() => presenter.popUpClosed()}>
        <PlayingCardGroup presenterGenerator={(view) => presenter.makeGroupPresenter(view)}/>
      </PopUp>}
    </div>
  )
}
export default CardStack