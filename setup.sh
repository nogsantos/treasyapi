#!/bin/bash

echo Setting up

export DB_URL=localhost:5432/treasy
export DB_TEST=localhost:5432/treasy_test
export DB_USER=postgres
export DB_PASSWORD=123456

echo Done!

echo Database: $DB_URL
echo Database Teste:  $DB_TEST
echo Database User: $DB_USER
echo Database Password: $DB_PASSWORD

# echo Building...

# if source ./gradlew init; then
# 	echo Success Builded
# else
# 	echo Build Failed
# fi

