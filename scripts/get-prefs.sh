#!/usr/bin/env bash

PROTO="http"
HOST="localhost"
PORT="8080"

curl -vv -H "X-API-Key: f146838f-9cd7-4904-a1a8-ea8076d6cd25" \
    ${PROTO}://${HOST}:${PORT}/api/v1/preferences/galapas@gmail.com
