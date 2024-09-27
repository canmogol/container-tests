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
open http://localhost:3000
```

## Dockerize

Build:
```sh
docker build --pull -t bun-hello-world .
```

Run:
```sh
docker run --rm -it -p 8080:3000 --cpus=0.2 --memory=16m --name bun-hello-world docker.io/library/bun-hello-world
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
```

