#!/bin/bash

function search_match_all() {
  curl -X GET "localhost:9200/bank/_search?pretty" -H 'Content-Type: application/json' -d'
  {
    "query": { "match_all": {} },
    "sort": [
      { "account_number": "asc" }
    ]
  }
'
}

function search_match_all_size() {
  curl -X GET "localhost:9200/bank/_search?pretty" -H 'Content-Type: application/json' -d'
  {
    "query": { "match_all": {} },
    "sort": [
      { "account_number": "asc" }
    ],
    "from": 10,
    "size": 10
  }
'
}

function search_match() {
  curl -X GET "localhost:9200/bank/_search?pretty" -H 'Content-Type: application/json' -d'
  {
    "query": { "match": { "address": "mill lane" } }
  }
'
}

function search_match_phrase() {
  curl -X GET "localhost:9200/bank/_search?pretty" -H 'Content-Type: application/json' -d'
  {
    "query": { "match_phrase": { "address": "mill lane" } }
  }
'
}

function search_match_exclude() {
  curl -X GET "localhost:9200/bank/_search?pretty" -H 'Content-Type: application/json' -d'
  {
    "query": {
      "bool": {
        "must": [
          { "match": { "age": "40" } }
        ],
        "must_not": [
          { "match": { "state": "ID" } }
        ]
      }
    }
  }
'
}

function search_match_filter() {
  curl -X GET "localhost:9200/bank/_search?pretty" -H 'Content-Type: application/json' -d'
  {
    "query": {
      "bool": {
        "must": { "match_all": {} },
        "filter": {
          "range": {
            "balance": {
              "gte": 20000,
              "lte": 30000
            }
          }
        }
      }
    }
  }
'
}

case $1 in
  match_all | batch_all_size | match | match_phrase | match_exclude | match_filter)
    search_$1
    ;;
  *)
    echo "match_all | match_all_size | match | match_phrase | match_exclude | match_filter"
    ;;
esac

