#!/bin/sh
# Maven Wrapper Shell Script for CI/CD & Linux environments
# Student: 23102A0003 Siddhesh Rajgure

PRG="$0"
while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done
PRGDIR=`dirname "$PRG"`

if command -v mvn >/dev/null 2>&1; then
    exec mvn "$@"
else
    echo "Using system mvn or wrapper..."
    exec mvn "$@"
fi
