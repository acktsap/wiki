import * as React from 'react';

import styles from './styles.module.scss';

class Option extends React.Component {
  render() {
    return (
      <h1 className={styles.option}>
        Test option
      </h1>
    );
  }
}

export default Option; 
