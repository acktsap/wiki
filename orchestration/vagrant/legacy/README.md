# Vagrant

## Setup

Need virtual box

1. [Install virtual box](https://www.virtualbox.org/wiki/Downloads)
2. [Install vagrant](https://www.vagrantup.com/downloads.html)

## Make virtual machine

By hand

```sh
> # make target directory
> mkdir some_dir
>
> # move Vagrantfile and some scripts
> cp Vagrantfile_xxx some_dir/Vagrantfile
> cp -R install-scripts/* some_dir/
> 
> # move to target directory
> cd some_dir
>
> # customize install scripts
> # ...
>
> # run vagrant file
> vagrant up
```

Use setup script

```sh
> ./setup.sh -p ubuntu -d test
```

## Access

By rsa config

```sh
> ssh xxx.xxx.xxx.xx
```

By vagrant (at target directory)

```sh
> vagrant ssh
```

## Commands

```sh
# create 'Vagrantfile'
vagrant init

# make vm based on 'Vagrantfile'
vagrant up

# check vm status
vagrant status

# ssh on vm (current directory is where 'vagrant up' command executed)
vagrant ssh

# stop vm
vagrant halt

# sleep vm
vagrant suspend

# awake vm
vagrant resume

# reload vm
vagrant reload

# remove vm
vagrant destroy
```
