#!/bin/bash

export readonly MYSQL_VERSION=${MYSQL_VERSION:-8.0.18}
export readonly MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD:-rootpw}

export readonly DB1_PORT=${DB1_PORT:-3306}
export readonly DB2_PORT=${DB2_PORT:-3307}

docker-compose -f ./docker-compose.yml "$@"
