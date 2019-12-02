#!/bin/bash

export readonly ES_VERSION=${ES_VERSION:-7.4.2}
export readonly ES_JAVA_OPTS=${ES_JAVA_OPTS:-"
-Xms512m -Xmx512m
"
}

export readonly ES1_PORT=${ES1_PORT:-9200}
export readonly ES2_PORT=${ES2_PORT:-9201}
export readonly KIBANA_PORT=${KIBANA_PORT:-5601}

docker-compose -f ./docker-compose.yml "$@"
