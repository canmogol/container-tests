# Bun - Hono

## Local Development

Install:
```sh
bun install
```

Run:
```sh
bun run dev
```

Open:
```sh
open http://localhost:8080
```

## Dockerize

Build:
```sh
docker build --pull -t bun-hello-world .
```

Run:
```sh
docker run --rm -it -p 8080:8080 --cpus=0.2 --memory=16m --name bun-hello-world docker.io/library/bun-hello-world
```

Test:
```sh
curl -v --max-time 2  http://localhost:8080/
```

Observe:
```sh
docker stats bun-hello-world
```

Load Test:
```sh
ab -c 100 -n 10000 http://localhost:8080/
# WITH NGINX
wrk -t10 -c500 -d30s --latency http://localhost:8080/external
```

