#!/bin/bash

sigterm() {
  echo "Caught SIGTERM signal!!"
  kill -TERM "$CHILD" 2 > /dev/null
}

trap sigterm SIGTERM

mkdir -p ./log
JAVA_OPTS="-server -Xms3G -Xmx3G -verbose:gc -Xlog:gc*:file=./log/gc.log:time::filecount=20,filesize=1M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=heap-nelo.hprof"
CLASSPATH=$(find ./lib -name '*.jar' | tr '\n' ':')
COMMAND="$JAVA_OPTS -cp ${CLASSPATH} acktsap.spring.application.WebApplicationKt"

echo "${COMMAND}"
java ${COMMAND} $@ &

CHILD=$!
echo "Waiting process ends.. (pid: $CHILD)"
wait "$CHILD"

echo "Wait 10 seconds before pod to be terminated"
sleep 10
