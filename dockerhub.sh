#!/bin/bash

mvn clean install -DskipTests
docker build --platform=linux/amd64 -t train-scheduler:latest .
docker tag train-scheduler:latest yourdockeraccount/train-scheduler:latest
docker push yourdockeraccount/train-scheduler:latest
