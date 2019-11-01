#!/bin/bash

function aggregation_group() {
  curl -X GET "localhost:9200/bank/_search?pretty" -H 'Content-Type: application/json' -d'
  {
    "size": 0,
    "aggs": {
      "group_by_state": {
        "terms": {
          "field": "state.keyword"
        }
      }
    }
  }
'
}

function aggregation_avg() {
  curl -X GET "localhost:9200/bank/_search?pretty" -H 'Content-Type: application/json' -d'
  {
    "size": 0,
    "aggs": {
      "group_by_state": {
        "terms": {
          "field": "state.keyword"
        },
        "aggs": {
          "average_balance": {
            "avg": {
              "field": "balance"
            }
          }
        }
      }
    }
  }
'
}

function aggregation_order() {
  curl -X GET "localhost:9200/bank/_search?pretty" -H 'Content-Type: application/json' -d'
  {
    "size": 0,
      "aggs": {
        "group_by_state": {
          "terms": {
            "field": "state.keyword",
            "order": {
            "average_balance": "desc"
            }
          },
          "aggs": {
            "average_balance": {
            "avg": {
              "field": "balance"
            }
          }
        }
      }
    }
  }
'
}


case $1 in
  group | avg | order)
    aggregation_$1
    ;;
  *)
    echo "group | avg | order"
    ;;
esac

