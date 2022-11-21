# File & Directory Management

- [ls](#ls)
- [cd](#cd)
- [cp](#cp)
- [ln](#ln)
- [mv](#mv)
- [rm](#rm)
- [mkdir](#mkdir)
- [rmdir](#rmdir)
- [chmod](#chmod)
- [chown](#chown)

## ls

- List directory contents.

List direcotory.

```sh
ls
```

List directory long.

```sh
ls -l
```

List directory long with human friendly file size.

```sh
# 보통의 ll alias
ls -lh
```

How hidden files (starts with .)

```sh
ls -a
```

Display a slash (`/`)

```sh
ls -F
```

종합

```sh
ls -lahF
```

## cd

- Change directory.

Change directory to relative a.

```sh
cd ./a
```

Parent directory.

```sh
cd ..
```

Parent of Parent directory.

```sh
cd ../..
```

Go to home directory.

```sh
cd ~
```

Go to home directory (empty).

```sh
cd
```

Go to previous directory.

```sh
cd -
```

## cp

```sh
```

## ln

- Link.

Make a symbolic link.

```sh
ln -s <source> <target>
```

Make a hard link.

```sh
ln <source> <target>
```

## mv

## rm

Remove force & recursively.

```sh
rm -rf ./test
```

## mkdir

- Make directory.

Make diredctory.

```sh
mkdir test1
```

Make diredctory including parent one.

```sh
mkdir -p test1/test2/test3
```

## rmdir

- Remove directory.

```sh
rmdir test
```

## chmod

## chown