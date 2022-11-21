# Process Monitoring

- [ps](#ps)
- [top](#top)
- [lsof](#lsof)
- [See also](#see-also)

## ps

- process status

Show all processes.

```sh
ps aux
```

Show all processes including commandline arguments.

```sh
ps -AFl
```

Show all processes with threads in tree mode


```sh
ps -AlFH
```

Show information for a particular process

```sh
ps -p <pid>
```

```sh
ps aux | grep <process_name>
```

## top

- display and update sorted information about processes
  - FD : File Descriptor
    - cwd : current working directory
    - rtd : root directory
    - txt : program text (code and data)
    - men : memory mapped file
    - 1u, 2r : actual file descriptor
      - r : read
      - w : write
      - u : read & write
  - Type
    - DIR : directory
    - REG : regular file
    - CHR : character special file
    - FIFO : first in first out

## lsof

- list open files

list all open files

```sh
lsof
```

List open file of specific user

```sh
lsof -u <user>
```

List IPv4 open files

```sh
lsof -i 4
```

List IPv6 open files

```sh
lsof -i 6
```

List open files of TCP port 1-1024

```sh
lsof -i TCP:1-1024
```

Exclude root user.

```sh
lsof -i -u ^root
```

Find Out who’s Looking What Files and Commands?

```sh
lsof -i -u acktsap
```

List all Network Connections (LISTENING & ESTABLISHED)

```sh
lsof -i
```

Search by PID.

```sh
lsof -p <pid>
```

List all pids of user.

```sh
lsof -t -u acktsap
```

Kill process using port 8080

```sh
# print 2nd column of 2nd line and kill
lsof -i :8080 | awk 'FNR == 2 { print $2 }' | xargs kill -9
```

## See also

- [PS Cheatsheet](https://www.sysadmin.md/ps-cheatsheet.html)
- [top (the UNIX process inspector) cheat sheet](https://gist.github.com/ericandrewlewis/4983670c508b2f6b181703df43438c37)
- [Lsof Commands Cheatsheet](https://neverendingsecurity.wordpress.com/2015/04/13/lsof-commands-cheatsheet/)