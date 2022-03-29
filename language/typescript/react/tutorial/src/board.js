import React from 'react';
import './index.css';
import Square from './square'

// To collect data from multiple children, or to have two child components communicate with each other, you need to declare the shared state in their parent component instead

export default class Board extends React.Component {

  renderSquare(i) {
    return (
      // pass props
      // the Square components are now controlled components
      // the Board has full control over it
      <Square
        value={this.props.squares[i]}
        onClick={() => this.props.onClick(i)}
      />
    )
  }

  render() {
    /*
      following code will be converted to

      return React.createElement('div', {className: 'status'},
        React.createElement('div', ...),
        ...
      );
    */
    return (
      <div>
        <div className='board-row'>
          {this.renderSquare(0)}
          {this.renderSquare(1)}
          {this.renderSquare(2)}
        </div>
        <div className='board-row'>
          {this.renderSquare(3)}
          {this.renderSquare(4)}
          {this.renderSquare(5)}
        </div>
        <div className='board-row'>
          {this.renderSquare(6)}
          {this.renderSquare(7)}
          {this.renderSquare(8)}
        </div>
      </div>
    )
  }

}