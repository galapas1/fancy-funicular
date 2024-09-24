#!/usr/bin/env bash

PROTO="http"
HOST="localhost"
PORT="8080"

curl ${PROTO}://${HOST}:${PORT}/v1/api/health
