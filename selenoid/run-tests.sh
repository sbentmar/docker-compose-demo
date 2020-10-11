
docker run -it --rm --network=host --name selenide-demo -v "$(pwd)/selenide/":/usr/src/mymaven -w /usr/src/mymaven maven:3.3-jdk-8 mvn clean install
