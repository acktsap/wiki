# Rest mock

Test project using swagger & prism.

## Usage

- Install : `yarn install`
- Run mock : `yarn run mock -d --errors -p {PORT} ${PATH_TO_SPEC}` (eg. `yarn run mock -d --errors ./spec/oas3.yaml`)
  ```sh
  --port, -p          Port that Prism will run on.
  --errors            Specifies whether request/response violations marked as errors will produce an error response      [boolean] [required] [default: false]
  --dynamic, -d       Dynamically generate examples.
  ```

## References

- [Prism](https://github.com/stoplightio/prism)
- [Prism docs](https://github.com/stoplightio/prism/tree/master/docs)
