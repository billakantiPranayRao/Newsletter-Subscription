#!/bin/bash

# Building the Spring Boot project using Maven
mvn clean package

# To build the Docker image
docker build -t ynewsletter-subscription-docker.jar .
