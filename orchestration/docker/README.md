# Docker

- [Images](#images)
- [Container](#container)
- [Dockerfile](#dockerfile)
- [Why containers stop immediately](#why-containers-stop-immediately)
- [References](#references)

## Images

```sh
# check images
docker images

# remove image
docker rmi ${IMAGE_ID}
```

## Container

```sh
# create container

## with port mapping
docker create ${CONTAINER_ID} -p ${HOST_PORT}:${CONTAINER_PORT}[tcp/udp]

## eg. (tcp: 80 of container to 8080 of host, udp: 80 of container to 8080 of host)
docker create ${CONTAINER_ID} -p 8080:80/tcp -p 8080:80/udp


# create & run container

## run
docker run ${IMAGE_ID}

## run in detach mode
docker run -d ${IMAGE_ID}


# start, stop, remove

## start container
docker start ${CONTAINER_ID}

## stop
docker stop ${CONTAINER_ID}

## remove all containers
docker ps -qa | xargs docker rm -f


# check

# containers
docker ps -a

# id only
docker ps -qa

# run bash
docker exec -it ${CONTAINER_ID} /bin/bash
```

## Dockerfile

https://docs.docker.com/engine/reference/builder/

## Why containers stop immediately

https://www.tutorialworks.com/why-containers-stop/

- main process가 죽으면 죽음. 그래서 계속 살려둬야함.

## References

[docker official reference](https://docs.docker.com/get-docker/)
