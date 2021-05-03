import * as React from 'react'

import Button from './button'

class Popup extends React.Component {
  render() {
    return (
      <div>
        <Button value="Frog" />
        <Button value="Turtle" />
        <Button value="Snake" />
        <Button value="Reset" />
      </div>
    )
  }
}

export default Popup;