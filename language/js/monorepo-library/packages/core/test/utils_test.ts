import chai from 'chai';
import chaiAsPromised from 'chai-as-promised';
chai.use(chaiAsPromised);
const assert = chai.assert;

import * as utils from '../src/utils';

describe('Adder', () => {
  it('should return sum of two values', () => {
    assert.equal(3, utils.add(1, 2));
  });

  it('should return zero on sum of two zeros', () => {
    assert.equal(0, utils.add(0, 0));
  });
});

describe('Subtractor', () => {
  it('should return subtract of two values', () => {
    assert.equal(2, utils.subtract(3, 1));
  });

  it('should return zero for substract of two zeros', () => {
    assert.equal(0, utils.subtract(0, 0));
  });
});