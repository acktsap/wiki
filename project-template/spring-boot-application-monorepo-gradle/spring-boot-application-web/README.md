# Spring Boot Application Web

## Docker

Make docker images

```sh
docker rmi acktsap && docker build -t acktsap .
```

Check image

```sh
docker image ls
```

Run container

```sh
docker run --name acktsap -d acktsap
```
