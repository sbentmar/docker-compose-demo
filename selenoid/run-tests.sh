#!/usr/bin/bash -x
read BASEURL
docker run -it --rm --network=host --name selenide-demo -v "$(pwd)/selenide/":/usr/src/mymaven -v "$(pwd)/.m2:/root/.m2" -w /usr/src/mymaven -e BASEURL=$BASEURL maven:3.3-jdk-8 mvn clean install
