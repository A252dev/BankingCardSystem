#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRESQL_USER" <<-EOSQL
    GRANT ALL PRIVILEGES ON DATABASE cabinet TO postgres;
EOSQL
