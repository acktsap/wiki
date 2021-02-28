#!/bin/bash

curl -X POST "localhost:9200/bank/_bulk?pretty&refresh" -H "Content-Type: application/json" --data-binary "@accounts.json"
