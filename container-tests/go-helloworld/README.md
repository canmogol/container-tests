# Deno - Hono

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
docker build -t go-hello .
```

Run:
```sh
docker run --rm -it -p 8080:8080 --cpus=0.2 --memory=16m --name go-hello docker.io/library/go-hello
```

Test:
```sh
curl -v --max-time 2  http://localhost:8080/
```

Observe:
```sh
docker stats go-hello
```

Load Test:
```sh
ab -c 100 -n 10000 http://localhost:8080/
```

