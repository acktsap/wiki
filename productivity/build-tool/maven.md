# Maven Related Tips

## Commands

Print submodules

```sh
> mvn help:evaluate -Dexpression=project.modules
```

Run goal on a specific module

```sh
> mvn test -pl subproject
```

project dependency is only visible to test

https://stackoverflow.com/questions/51180648/how-do-i-turn-off-the-visible-only-for-test-sources-feature-in-eclipse-photon
