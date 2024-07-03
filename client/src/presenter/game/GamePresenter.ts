import {MessagePresenter, MessageView} from "../MessagePresenter";

export interface GameView extends MessageView {}

export abstract class GamePresenter<T extends GameView> extends MessagePresenter<T>{

}