import { Student } from "./student";

export class EagerStucent implements Student {
  readonly name: string;
  readonly age: number;

  protected lazy = false;

  isLazy(): boolean {
    return this.lazy;
  }

}