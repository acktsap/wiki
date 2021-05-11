import chai from 'chai';
import chaiAsPromised from 'chai-as-promised';
chai.use(chaiAsPromised);
const assert = chai.assert;

import Subtractor from '../src/subtractor';

describe('Subtractor', () => {
  it('should return subtract of two values', () => {
    const subtractor: Subtractor = new Subtractor();
    assert.equal(2, subtractor.subtract(3, 1));
  });

  it('should return zero for substract of two zeros', () => {
    const subtractor: Subtractor = new Subtractor();
    assert.equal(0, subtractor.subtract(0, 0));
  });
});
