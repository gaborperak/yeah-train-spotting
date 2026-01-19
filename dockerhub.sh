#!/bin/bash

mvn clean install -DskipTests
docker build --platform=linux/amd64 -t train-scheduler:latest .
docker tag train-scheduler:latest gaborperak182/train-scheduler:latest
docker push gaborperak182/train-scheduler:latest