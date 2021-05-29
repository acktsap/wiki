import * as React from 'react';

import styles from './styles.module.scss';
import Button from './button';

interface PopupProps {}

interface PopupState {
  name: string;
}

class Popup extends React.Component<PopupProps, PopupState> {
  constructor(props: PopupProps) {
    super(props);
    this.state = {
      name: "State1"
    };
  }

  render() {
    return (
      <div className={styles.popup}>
        <Button name={this.state.name} onClick={(e) => this.onClick(e)} />
        <Button name="Turtle" />
        <Button name="Snake" />
        <Button name="Reset" />
      </div>
    );
  }

  onClick(e: React.MouseEvent<HTMLElement>) {
    let nextState;
    // TODO: why print <unavailable>?
    console.log("event target", e.target);
    console.log("currrent state: " + this.state.name);

    if (this.state.name === "State1") {
      nextState = "State2";
    } else {
      nextState = "State1";
    }

    this.setState({
      name: nextState
    });
  }
}

export default Popup;