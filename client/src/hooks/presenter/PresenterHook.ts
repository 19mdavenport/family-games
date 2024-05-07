import {useEffect, useState} from "react";

export interface PresenterProps<V, P> {
  presenterGenerator(view: V): P;
}

export default function usePresenter<V, P>(props: PresenterProps<V, P>, view: V): P | null {
  const [presenter , setPresenter] = useState<P | null>(null);
  useEffect(() => setPresenter(props.presenterGenerator(view)), []);
  return presenter;
}