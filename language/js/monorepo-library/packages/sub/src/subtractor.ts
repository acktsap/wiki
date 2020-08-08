import logger from 'loglevel';

/**
 * A subtractor
 */
export class Subtractor {
  private delegate: Function;

  /**
   * {@code Subtractor} constructor.
   */
  public constructor() {
    this.delegate = (l: number, r: number): number => l - r;
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
