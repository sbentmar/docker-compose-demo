# Starting locally

Install docker and docker-compose, then:
```
docker-compose up --build
```

This must be done from the directory containing docker-compose.yml.

# Starting on AWS EC2
Start an EC2 instance running Amazon Linux 2.

Make sure your security group permits access to port 80.

```bash
sudo yum update -y
sudo amazon-linux-extras install docker
sudo yum install git -y
sudo service docker start
sudo curl -L "https://github.com/docker/compose/releases/download/1.27.4/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose

git clone https://github.com/sbentmar/docker-compose-demo
cd docker-compose-demo
sudo docker-compose up --build
```


# Running tests

Start selenoid:
```
cd selenoid
./setup.sh
```
Then, run the tests:
```
cd selenoid
./run-tests.sh
```

# Saving database to new image

Assuming your redis container is running and named `dockerdemo_redis_1` (check with `docker ps`)

```
docker exec -it dockerdemo_redis_1 redis-cli save
docker commit dockerdemo_redis_1 redis:databasedump
```

This will create a new docker image named "databasedump" that contains your data.

To use your dumped image rather than the data from the `redis` directory, update the docker-compose.yml file. Replace
```
  redis:
    build: redis/
```
with
```
  redis:
    image: redis:databasedump
```

Then run start docker-compose like normal.

# Shutting down
To shut down docker-compose, use `docker-compose down` from the directory containing docker-compose.yml.
