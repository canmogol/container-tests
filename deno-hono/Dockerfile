FROM denoland/deno:latest

EXPOSE 8080

WORKDIR /app

USER deno

COPY . .
RUN deno cache main.ts

CMD ["run", "-A", "main.ts"]
