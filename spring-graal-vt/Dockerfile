FROM container-registry.oracle.com/graalvm/graalvm-community:latest as builder
WORKDIR /
ADD . /
COPY pom.xml .

RUN ["/bin/bash", "-c", "cd / \
&& chmod +x ./mvnw \
&& ./mvnw clean \
&& ./mvnw  -Pnative native:compile \
"]

FROM ubuntu:22.10
COPY --from=builder /target/spring-graal-vt /spring-graal-vt
EXPOSE 8080
CMD ["/spring-graal-vt"]
