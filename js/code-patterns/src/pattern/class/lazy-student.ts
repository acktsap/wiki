import { Student } from "./student";

export class LazyStudent implements Student {
  readonly name: string;
  readonly age: number;

  protected lazy = true;

  isLazy(): boolean {
    return this.lazy;
  }
}