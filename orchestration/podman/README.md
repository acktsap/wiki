# Podman

- [Introduction](#introduction)
- [vs Docker](#vs-docker)
- [Installation](#installation)
  - [OSX](#osx)
- [Commands](#commands)
- [Tutorials](#tutorials)
- [References](#references)

## Introduction

- A daemonless, open source, Linux native tool designed to make it easy to find, run, build, share and deploy applications using Open Containers Initiative (OCI) Containers and Container Images.
- Provides a command line interface (CLI) familiar to anyone who has used the Docker Container Engine. - Most users can simply alias Docker to Podman (`alias docker=podman`) without any problem

## vs Docker

- Docker
  - Run on docker daemon.  
    -> daemon이 죽으면 모든 container가 죽음.
  - Only root is allowed.
- Podman
  - Daemon-less. Run by fork/exec.  
    -> daemon이 죽으면 모든 container가 죽는 문제 없음.
  - Both root and non-privileged user are allowed.

## Installation

### OSX

- Podman은 linux용이므로 linux box 안에서 돌아감.

```sh
# install
brew install podman

# start linux server for podman
podman machine init
podman machine start

# show status
podman info
```

[see also](https://podman.io/getting-started/installation)

## Commands

https://docs.podman.io/en/latest/Commands.html

## Tutorials

https://docs.podman.io/en/latest/Tutorials.html

## References

- [Podman (offical)](https://docs.podman.io/en/latest/index.html)