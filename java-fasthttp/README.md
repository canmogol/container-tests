# FusionAuth/java-http Application

Uses FusionAuth/java-http server, HTTP client and virtual threads for both of them.

## Local Development

Run:
```sh
mvn clean install
java -jar target/java-fasthttp-1.0-SNAPSHOT.jar
```

Open:
```sh
open http://0.0.0.0:8080/
```

## Dockerize

Build:
```sh
docker build -t java-fasthttp:0.0.1 .
```

Run:
```sh
docker run --rm -it -p 8080:8080 --cpus=0.2 --memory=16m --name java-fasthttp docker.io/library/java-fasthttp:0.0.1
```

Test:
```sh
curl -v --max-time 2  http://localhost:8080/
```

Observe:
```sh
docker stats java-fasthttp
```

Load Test:
```sh
ab -c 100 -n 10000 http://localhost:8080/
# WITH NGINX
wrk -t10 -c500 -d30s --latency http://localhost:8080/external
```

