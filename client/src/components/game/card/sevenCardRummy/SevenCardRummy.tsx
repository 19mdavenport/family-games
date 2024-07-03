import React from "react";
import PlayingCardGroup from "../PlayingCardGroup";
import UserInfo from "../../UserInfo";
import CardStack from "../CardStack";
import {
  SevenCardRummyPresenter,
  SevenCardRummyView
} from "../../../../presenter/game/card/sevenCardRummy/SevenCardRummyPresenter";
import usePresenter, {PresenterProps} from "../../../../hooks/presenter/PresenterHook";
import useToastListener from "../../../../hooks/toaster/ToastListenerHook";
import PlayingCard from "../PlayingCard";

const SevenCardRummy = (props: PresenterProps<SevenCardRummyView, SevenCardRummyPresenter>) => {
  const [cardSelected, setCardSelected] = React.useState<boolean>(false);
  const presenter = usePresenter(props, {...useToastListener(), setCardSelected});

  if(!presenter) return (<></>);
  const pres = presenter as SevenCardRummyPresenter;
  return (
    <>
      <PlayingCardGroup presenterGenerator={(view) => pres.makeOpponentHandPresenter(view)}/>
      <UserInfo style={{position: "absolute", top: "31%", left: "50%", transform: "translate(-50%, 0)", height: "5%", width: "fit-content"}}/>

      <CardStack presenterGenerator={(view) => pres.makeDiscardPile(view)}/>
      <CardStack presenterGenerator={(view) => pres.makeDrawPile(view)}/>

      <UserInfo style={{position: "absolute", bottom: "22%", left: "50%", transform: "translate(-50%, 0)", height: "5%", width: "fit-content"}}/>
      <PlayingCardGroup presenterGenerator={(view) => pres.makeUserHandPresenter(view)}/>
      {cardSelected && <PlayingCard presenterGenerator={(view) => pres.makeSelectedCard(view)} />}
    </>
  )
}
export default SevenCardRummy