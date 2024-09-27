FROM denoland/deno:1.46.3

EXPOSE 8080

WORKDIR /app

USER deno

COPY . .
RUN deno cache main.ts

CMD ["run", "--allow-net", "main.ts"]