export class GameEvent {
  private readonly _timestamp: number
  private readonly _text: string

  constructor(text: string, timestamp: number) {
    this._timestamp = timestamp
    this._text = text
  }


  get timestamp(): number {
    return this._timestamp;
  }

  get text(): string {
    return this._text;
  }
}