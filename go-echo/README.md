# GO STD LIB

## Local Development

Run:
```sh
go run main.go
```

Open:
```sh
open http://0.0.0.0:8080/
```

## Dockerize

Build:
```sh
docker build -t go-echo .
```

Run:
```sh
docker run --rm -it -p 8080:8080 --cpus=0.2 --memory=16m --name go-echo docker.io/library/go-echo
```

Test:
```sh
curl -v --max-time 2  http://localhost:8080/
```

Observe:
```sh
docker stats go-echo
```

Load Test:
```sh
ab -c 100 -n 10000 http://localhost:8080/external
# WITH NGINX
wrk -t10 -c500 -d30s --latency http://localhost:8080/external
```
