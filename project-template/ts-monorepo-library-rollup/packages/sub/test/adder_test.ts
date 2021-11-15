import chai from 'chai';
import chaiAsPromised from 'chai-as-promised';
chai.use(chaiAsPromised);
const assert = chai.assert;

import Adder from '../src/adder';

describe('Adder', () => {
  it('should return sum of two values', () => {
    const adder: Adder = new Adder();
    assert.equal(3, adder.add(1, 2));
  });

  it('should return zero on sum of two zeros', () => {
    const adder: Adder = new Adder();
    assert.equal(0, adder.add(0, 0));
  });
});
