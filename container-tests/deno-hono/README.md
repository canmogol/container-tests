# Deno - Hono

## Local Development

Run:
```sh
deno task start
```

Open:
```sh
open http://0.0.0.0:8080/
```

## Dockerize

Build:
```sh
docker build -t deno-server .
```

Run:
```sh
docker run --rm -it -p 8080:8080 --cpus=0.2 --memory=16m --name deno-server deno-server:latest
```

Test:
```sh
curl -v --max-time 2  http://localhost:8080/
```

Observe:
```sh
docker stats deno-server
```

Load Test:
```sh
ab -c 100 -n 10000 http://localhost:8080/
```

