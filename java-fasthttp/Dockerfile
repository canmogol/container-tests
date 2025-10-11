FROM ghcr.io/graalvm/jdk-community:25 AS builder
WORKDIR /
COPY . /

RUN ["/bin/bash", "-c", "cd / \
&& chmod +x ./mvnw \
&& ./mvnw clean install \
&& native-image --version \
&& native-image --enable-preview --no-fallback -H:+ReportExceptionStackTraces -jar target/VirtualThreadApplication.jar \
"]

FROM ubuntu:22.10
COPY --from=builder /VirtualThreadApplication /VirtualThreadApplication
EXPOSE 8080
CMD ["/VirtualThreadApplication"]
