# GO FIBER

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
docker build -t go-fiber .
```

Run:
```sh
docker run --rm -it -p 8080:8080 --cpus=0.2 --memory=16m --name go-fiber docker.io/library/go-fiber
```

Test:
```sh
curl -v --max-time 2  http://localhost:8080/
```

Observe:
```sh
docker stats go-fiber
```

Load Test:
```sh
ab -c 100 -n 10000 http://localhost:8080/
# WITH NGINX
wrk -t10 -c500 -d30s --latency http://localhost:8080/external
```

