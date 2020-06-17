# Docker

## Create Container

### Port Mapping

```sh
# port mapping
docker create ${container_id} -p ${host_port}:${container_port}[tcp/udp]

# eg. (tcp: 80 of container to 8080 of host, udp: 80 of container to 8080 of host)
docker create ${container_id} -p 8080:80/tcp -p 8080:80/udp
```

## References

[docker official reference](https://docs.docker.com/get-docker/)
