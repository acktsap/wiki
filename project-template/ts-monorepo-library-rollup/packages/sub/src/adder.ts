import logger from 'loglevel';

/**
 * An adder
 */
export class Adder {
  private delegate: Function;

  /**
   * {@code Adder} constructor.
   */
  public constructor() {
    this.delegate = (l: number, r: number): number => l + r;
  }

  /**
   * Returns {@code left + right}
   *
   * @param {Number} left a left value
   * @param {Number} right a right value
   * @return {Number} an addition result
   */
  public add(left: number, right: number): number {
    const ret = this.delegate(left, right);
    logger.debug('Sum of', left, 'and', 'is', ret);
    return ret;
  }
}

export default Adder;
