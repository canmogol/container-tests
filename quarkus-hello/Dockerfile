FROM container-registry.oracle.com/graalvm/graalvm-community:latest as builder
WORKDIR /
ADD . /
COPY mvnw .
COPY pom.xml .

RUN ["/bin/bash", "-c", "cd / \
&& chmod +x ./mvnw \
&& ./mvnw clean \
&& ./mvnw package -Dnative \
"]

FROM ubuntu:22.10
COPY --from=builder /target/quarkus-hello /quarkus-hello
EXPOSE 8080
CMD ["/quarkus-hello"]

FROM quay.io/quarkus/ubi9-quarkus-micro-image:2.0
WORKDIR /work/
RUN chown 1001 /work \
    && chmod "g+rwX" /work \
    && chown 1001:root /work
COPY --chown=1001:root --chmod=0755 --from=builder target/*-runner /work/application

EXPOSE 8080
USER 1001

ENTRYPOINT ["./application", "-Dquarkus.http.host=0.0.0.0"]