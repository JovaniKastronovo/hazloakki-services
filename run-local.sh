#!/usr/bin/env sh


cd hazloakki-service-registry
./gradlew clean buildImage

cd ../hazloakki-service-gateway
./gradlew clean buildImage


docker-compose up 
docker-compose stop
docker-compose rm -f
