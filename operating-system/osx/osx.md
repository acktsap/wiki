# osx

## Service

load, unload

```sh
> launchctl load /Library/LaunchAgents/aaa.bbb.ccc.plist
> launchctl unload /Library/LaunchAgents/aaa.bbb.ccc.plist
```

list

```sh
> launchctl list
> launchctl list <label>
```

dump state (check details like plist path)

```sh
> launchctl dumpstate
```

where daemon files is

- ~/Library/LaunchDaemons
- ~/Library/LaunchAgents
- /Library/LaunchDaemons
- /Library/LaunchAgents
- /System/Library/LaunchDaemons
- /System/Library/LaunchAgents


## System extension

list

```sh
> systemextensionsctl list
```
