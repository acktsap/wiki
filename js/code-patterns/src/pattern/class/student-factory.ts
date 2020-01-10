import { Student } from "./student";
import { LazyStudent } from "./lazy-student";
import { EagerStucent } from "./eager-student";

export default class StudentFactory {
  create(type: String): Student {
    switch (type) {
      case "eager":
        return new EagerStucent();
        break;
      case "lazy":
        return new LazyStudent();
        break;
      default:
        return new LazyStudent();
    }
  }
}