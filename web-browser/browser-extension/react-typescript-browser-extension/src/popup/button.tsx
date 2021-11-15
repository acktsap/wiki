import * as React from 'react';

import styles from './styles.module.scss';

type ButtonProps = {
  name: string;
  onClick: (e: React.MouseEvent<HTMLElement>) => void;
};

function Button({ name, onClick } : ButtonProps) {
  return (
    <div className={styles.button} onClick={onClick} >
      {name}
    </div>
  );
}

Button.defaultProps = {
  name: 'undefined',
  onClick: () => void(0),
};

export default Button;
