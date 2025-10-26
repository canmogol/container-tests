# Rust Axum

## Local Development

Run:
```sh
cargo run
```

Open:
```sh
open http://0.0.0.0:8080/
```

## Dockerize

Build:
```sh
docker build -t rust-axum .
```

Run:
```sh
docker run --rm -it -p 8080:8080 --cpus=0.2 --memory=16m --name rust-axum rust-axum:latest
```

Test:
```sh
curl -v --max-time 2  http://localhost:8080/
```

Observe:
```sh
docker stats rust-axum
```

Load Test:
```sh
ab -c 100 -n 10000 http://localhost:8080/
# WITH NGINX
wrk -t10 -c500 -d30s --latency http://localhost:8080/external
```

