import * as React from 'react';

import styles from './styles.module.scss';

type ButtonProps = {
  value: string;
};

function Button({ value } : ButtonProps) {
  return (
    <div className={styles.button}>
      {value}
    </div>
  );
}

Button.defaultProps = {
  value: 'undefined'
};

export default Button;
