import * as React from 'react'

import './button.scss'

const Button = (props: any) => {
  return (
    <div className='button'>
      {props.value}
    </div>
  )
}

export default Button;
