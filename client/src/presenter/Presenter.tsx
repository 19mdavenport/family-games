export interface View {

}

export abstract class Presenter<V extends View> {
  private _view: V;

  constructor(view: V) {
    this._view = view;
  }

  protected get view() {
    return this._view;
  }
}