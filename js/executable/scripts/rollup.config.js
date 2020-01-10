import commonjs from 'rollup-plugin-commonjs';
import resolve from 'rollup-plugin-node-resolve';
import babel from 'rollup-plugin-babel';
import { terser } from 'rollup-plugin-terser';
import pkg from '../package.json';

const extensions = [
  '.js', '.jsx', '.ts', '.tsx',
];

// `npm run build` -> `production` is true
// `npm run dev` -> `production` is false
const production = !process.env.ROLLUP_WATCH;

export default {
  input: './src/index.ts',

  // Specify here external modules which you don't want to include in your bundle (for instance: 'lodash', 'moment' etc.)
  // https://rollupjs.org/guide/en#external-e-external
  external: [],

  plugins: [
    // Locate modules using the Node resolution algorithm.
    resolve({ extensions }),

    // Allow bundling cjs modules. Rollup doesn't understand cjs
    commonjs(),

    // Compile latest ts/js features into oldest one. Need @babel/core
    babel({ extensions, include: ['src/**/*'] }),

    // minify, but only in production
    production && terser()
  ],

  output: [{
    file: pkg.main,
    format: 'cjs',
  }],
};
