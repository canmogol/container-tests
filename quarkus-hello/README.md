# Quarkus Example


## Local Development

Run:
```sh
mvn clean install
java -jar target/quarkus-app/quarkus-run.jar
```

Open:
```sh
open http://0.0.0.0:8080/
```

## Dockerize

Build:
```sh
docker build -t quarkus-hello .
```

Run:
```sh
docker run --rm -it -p 8080:8080 --cpus=0.2 --memory=16m --name quarkus-hello docker.io/library/quarkus-hello:0.0.1
```

Test:
```sh
curl -v --max-time 2  http://localhost:8080/
```

Observe:
```sh
docker stats quarkus-hello
```

Load Test:
```sh
ab -c 100 -n 10000 http://localhost:8080/
# WITH NGINX
wrk -t10 -c500 -d30s --latency http://localhost:8080/external
```

