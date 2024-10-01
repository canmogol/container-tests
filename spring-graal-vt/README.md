# Spring Boot with Virtual Threads Example


## Local Development

Run:
```sh
mvn clean install
java -jar target/spring-graal-vt-0.0.1-SNAPSHOT.jar
```

Open:
```sh
open http://0.0.0.0:8080/
```

## Dockerize

Build:
```sh
docker build -t spring-graal-vt .
```

Run:
```sh
docker run --rm -it -p 8080:8080 --cpus=0.2 --memory=16m --name spring-graal-vt docker.io/library/spring-graal-vt:0.0.1
```

Test:
```sh
curl -v --max-time 2  http://localhost:8080/
```

Observe:
```sh
docker stats spring-graal-vt
```

Load Test:
```sh
ab -c 100 -n 10000 http://localhost:8080/
```

