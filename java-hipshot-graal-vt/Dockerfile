FROM ghcr.io/graalvm/native-image-community:25 as builder
WORKDIR /
COPY . /

RUN ["/bin/bash", "-c", "cd / \
&& chmod +x ./mvnw \
&& ./mvnw clean install \
&& native-image --version \
&& native-image --enable-preview --no-fallback -H:+ReportExceptionStackTraces -jar target/RawVirtualThreadApplication.jar \
"]

FROM ubuntu:22.10
COPY --from=builder /HipshotServer /HipshotServer
EXPOSE 8080
CMD ["/HipshotServer"]
