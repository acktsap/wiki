# Vagrant

## Introduction

A tool for building and managing virtual machine environments in a single workflow.

## Install vagrant

https://www.vagrantup.com/downloads.html)

osx

```sh
brew install vagrant
```

## Install Provider

- [Virtual box](https://www.virtualbox.org/wiki/Downloads)

## See boxes

https://app.vagrantup.com/boxes/search

## Basic Commands

```sh
# make a new directory
mkdir some_dir
cd some_dir

# create 'Vagrantfile'
vagrant init $machine

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

## Other Commands

https://www.vagrantup.com/docs/cli

## References

- [vagrant learn](https://learn.hashicorp.com/vagrant)
