#!/usr/bin/env bash

PROTO="http"
HOST="localhost"
PORT="8080"

generate_post_data() {
  cat <<EOF
{
  "emailAddress": "galapas@gmail.com",
  "tags": [
    "onion", "olives", "peppers"
  ]
}
EOF
}

curl -d "$(generate_post_data)" \
     -H "X-API-Key: f146838f-9cd7-4904-a1a8-ea8076d6cd25" \
     -H "Content-type: application/json" \
    ${PROTO}://${HOST}:${PORT}/v1/api/preferences/
