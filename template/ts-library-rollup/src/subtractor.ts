import logger from 'loglevel';
import {subtract} from './utils';

/**
 * A subtractor
 */
export class Subtractor {
  private delegate: Function;

  /**
   * {@code Subtractor} constructor.
   */
  public constructor() {
    this.delegate = subtract;
  }

  /**
   * Returns {@code left - right}
   *
   * @param {Number} left a left value
   * @param {Number} right a right value
   * @return {Number} an subtraction result
   */
  public subtract(left: number, right: number): number {
    const ret = this.delegate(left, right);
    logger.debug('Substraction of', left, 'and', 'is', ret);
    return ret;
  }
}

export default Subtractor;
