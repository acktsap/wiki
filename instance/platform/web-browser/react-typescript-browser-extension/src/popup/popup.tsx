import * as React from 'react';

import styles from './styles.module.scss';
import Button from './button';

class Popup extends React.Component {
  render() {
    return (
      <div className={styles.popup}>
        <Button value="Frog" />
        <Button value="Turtle" />
        <Button value="Snake" />
        <Button value="Reset" />
      </div>
    );
  }
}

export default Popup;