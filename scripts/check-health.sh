#!/usr/bin/env bash

PROTO="http"
HOST="localhost"
PORT="8080"

curl -vv ${PROTO}://${HOST}:${PORT}/api/v1/health
