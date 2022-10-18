#!/bin/bash

PID=$(jcmd | grep "acktsap.spring.application.WebApplicationKt" | cut -d ' ' -f1)

echo "stopping.. (pid: ${PID})"
kill -15 ${PID}
exit 0
