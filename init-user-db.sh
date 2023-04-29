#!/bin/sh
set -e
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE DATABASE customer;
	CREATE DATABASE transaction;
	CREATE DATABASE notification;
	CREATE DATABASE fraud;
EOSQL