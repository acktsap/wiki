# Make package

## Hacking basic

Use [coffeescript](https://coffeescript.org/)

## Customization

Modify `~/.atom/init.coffee` if it's too big, create a package

## Package basic

Enter 'Generate Package' in the command palette

### Structure

```text
my-package/
├─ grammars/ : Grammars for language package
├─ snippets/ : Snippets for your package. Specially for language package
├─ keymaps/ : Key bindings for your package
├─ lib/ : Place your files into here
├─ menus/ : Additional application menu for your package
├─ spec/ : For spec test (run by Jasmine)
├─ styles/ : Custom style written in css or less (less is recommanded)
└─ package.json : contains metadata about the package
```

### Dependency on other package

[atom-package-deps](https://www.npmjs.com/package/atom-package-deps)

### Config

[Config](https://atom.io/docs/api/lastet/Config)

## Debugging

* Reload            : To see your changes, you must reload the code by command `window:reload`
* Developer Console : `cmd + alt + i` or _View > Developer > Toggle Developer Tools_

## Testing

Placed in the `spec` directory

* In atom     : `cmd + ctrl + alt + p` or _View > Developer > Run Package Specs_
* In terminal : `atom --test spec` in package root directory

## Installing

Install locally by linking

```sh
> cd ${project_home}
> atom link
```

## Publishing

With tag

```sh
> # update version of package.json
> git tag vX.X.X
> git push origin vX.X.X
> apm publish --tag vX.X.X
```

## See also

* [package-word-count](https://flight-manual.atom.io/hacking-atom/sections/package-word-count/)
* [atom api](https://atom.io/docs/api/v1.34.0/AtomEnvironment)
