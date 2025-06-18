#!/bin/bash

set -e

echo "before"

export PGUSER=postgres
echo $PGUSER

psql <<- EOSQL
    CREATE DATABASE cabinet;
    GRANT ALL PRIVILEGES ON DATABASE cabinet TO postgres;
EOSQL



echo "after."