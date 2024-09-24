#!/usr/bin/env bash

PROTO="http"
HOST="localhost"
PORT="8080"

generate_post_data() {
  cat <<EOF
{
  "emailAddr": "galapas@gmail.com",
  "tags": [
    "onion", "olives", "peppers"
  ]
}
EOF
}

curl -d "$(generate_post_data)" \
     -H "Content-type: application/json" \
    ${PROTO}://${HOST}:${PORT}/v1/api/preferences/
