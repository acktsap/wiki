import chai from 'chai';
import chaiAsPromised from 'chai-as-promised';
chai.use(chaiAsPromised);
const assert = chai.assert;

import Starter from '../src/starter';

describe('Starter', () => {
  it('should return 0', () => {
    assert.equal(0, Starter.main());
  });
});
