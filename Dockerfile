FROM eclipse-temurin:21 as builder
WORKDIR /
ADD . /
COPY pom.xml .

RUN ["/bin/bash", "-c", "cd / \
&& chmod +x ./mvnw \
&& ./mvnw clean install \
"]

FROM eclipse-temurin:21
WORKDIR /
COPY --from=builder /target/routing-0.1.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT java -XX:MaxRAMPercentage=100 -XX:MinRAMPercentage=100 -XshowSettings:vm -version && java -XX:MaxRAMPercentage=100 -XX:MinRAMPercentage=100 -Dr2dbc.postgresql.host=${DATABASE} -jar /app.jar