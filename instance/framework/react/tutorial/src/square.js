import React from 'react';
import './index.css';

// can define react component as function
// when doesn't need to have own state
export default function Square(props) {
  return (
    // use props
    <button className='square' onClick={props.onClick}>
      {props.value}
    </button>
  )
}