import {Presenter, View} from "./Presenter";

export interface MessageView extends View {
  displayInfoMessage(message: string, duration: number): void;
  clearLastInfoMessage(): void;
  displayErrorMessage: (message: string) => void;
}

export abstract class MessagePresenter<T extends MessageView> extends Presenter<T> {
  private retrieving: Map<string, boolean> = new Map();

  protected async doFailureReportingOperation(operation: () => Promise<void>, description: string) {
    if(!this.retrieving.get(description)) {
      this.retrieving.set(description, true);
      try {
        await operation();
      } catch (error) {
        this.view.displayErrorMessage(
          `Failed to ${description} because of exception: ${(error as Error).message}`
        );
      } finally {
        this.retrieving.set(description, false);
      }
    }
  }
}