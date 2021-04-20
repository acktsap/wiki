import React from 'react';
import Board from './board';
import './index.css';

export default class Game extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      history: [{
        squares: Array(9).fill(null)
      }],
      stepNumber: 0,
      xIsNext: true,
    };
  }

  handleClick(i) {
    // trim history to current stepNumber
    const history = this.state.history.slice(0, this.state.stepNumber + 1)
    const current = history[history.length - 1];

    // slice : create a copy of origin squares
    // immutability helps react to determine when to re-render
    const squares = current.squares.slice();

    if (this.calculateWinner(squares) || squares[i]) {
      return;
    }

    squares[i] = this.state.xIsNext ? 'X' : 'O';

    // trigger re-rendering
    this.setState({
      // concat method doesn't mutate the original array
      history: history.concat([{
        squares: squares,
      }]),
      stepNumber: history.length,
      xIsNext: !this.state.xIsNext,
    }) 
  }

  jumpTo(step) {
    console.log(step)
    this.setState({
      stepNumber: step,
      xIsNext: (step % 2) === 0,
    })
  }

  calculateWinner(squares) {
    const lines = [
      [0, 1, 2],
      [3, 4, 5],
      [6, 7, 8],
      [0, 3, 6],
      [1, 4, 7],
      [2, 5, 8],
      [0, 4, 8],
      [2, 4, 6],
    ];

    for (let i = 0; i < lines.length; ++i) {
      const [a, b, c] = lines[i];
      if (squares[a] != null 
          && (squares[a] === squares[b])
          && (squares[b] === squares[c])) {
        return squares[a];
      }
    }

    return null;
  }

  // render when this.state changes
  render() {
    const history = this.state.history;
    const current = history[this.state.stepNumber]
    const winner = this.calculateWinner(current.squares)

    let status;
    if (winner != null) {
      status = `Winner: ${winner}`
    } else {
      status = `Next player: ${this.state.xIsNext ? 'X' : 'O'}`;
    }

    // strongly recommended to assign proper keys when building dynamic lists
    const moves = history.map((step, move) => {
      const desc = move ? `Go to move #${move}` : 'Go to game start';
      return (
        // key : to determine which component to update  
        // it only needs to be unique between components
        <li key={move}>
          <button onClick={() => this.jumpTo(move)}>{desc}</button>
        </li>
      );
    })

    return (
      <div className='game'>
        <div className='game-board'>
          <Board
            squares={current.squares}
            onClick={(i) => this.handleClick(i)}
          />
        </div>
        <div className='game-info'>
          <div>{status}</div>
          <ol>{moves}</ol>
        </div>
      </div>
    )
  }
}