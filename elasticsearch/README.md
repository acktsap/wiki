# Elastic Search

## Keywords

- index
  - document, type, field (text, keyword)
- search
- aggregation
- clustering (for availibility, performance)

## Run as docker

- Create: `docker-compose -f ./es-docker-compose.yml up --no-start`
- Create & Run: `docker-compose -f ./es-docker-compose.yml up`
- Start/Stop/Rm: `docker-compose -f ./es-docker-compose.yml start/stop/rm`
- Check: `curl -X GET localhost:9200/_cat/nodes?v&pretty`

## Reference

- [Run docker guide](https://www.elastic.co/guide/en/elastic-stack-get-started/current/get-started-docker.html)
- [Basic play](https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started.html)
- [Rest api](https://www.elastic.co/guide/en/elasticsearch/reference/current/rest-apis.html)

