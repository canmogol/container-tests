# Hipshot Example Application

Uses Hipshot server.

## Local Development

Run:
```sh
mvn clean install
java -jar target/HipshotServer.jar
```

Open:
```sh
open http://0.0.0.0:8080/
```

## Dockerize

Build:
```sh
docker build -t java-hipshot-graal-vt:0.0.1 .
```

Run:
```sh
docker run --rm -it -p 8080:8080 --cpus=0.2 --memory=16m --name java-hipshot-graal-vt docker.io/library/java-hipshot-graal-vt:0.0.1
```

Test:
```sh
curl -v --max-time 2  http://localhost:8080/
```

Observe:
```sh
docker stats java-hipshot-graal-vt
```

Load Test:
```sh
ab -c 100 -n 10000 http://localhost:8080/
# WITH NGINX
wrk -t10 -c500 -d30s --latency http://localhost:8080/external
```

