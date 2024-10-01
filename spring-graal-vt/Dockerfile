FROM container-registry.oracle.com/graalvm/graalvm-community:latest as builder
WORKDIR /
ADD . /
COPY pom.xml .

RUN ["/bin/bash", "-c", "cd / \
&& curl https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz -o apache-maven-3.9.9-bin.tar.gz \
&& tar xfvz apache-maven-3.9.9-bin.tar.gz \
&& /apache-maven-3.9.9/bin/mvn -Pnative native:compile"]

FROM ubuntu:22.10
COPY --from=builder /spring-graal-vt/target/spring-graal-vt /spring-graal-vt
EXPOSE 8080
CMD ["/spring-graal-vt"]