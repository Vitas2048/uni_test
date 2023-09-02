FROM maven:3.9.0-eclipse-temurin-17 as builder

COPY pom.xml pom.xml
COPY src src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-focal as runtime

COPY --from=builder ./target/uni_test-0.0.1-SNAPSHOT.jar ./api.jar

CMD ["java", "-jar", "api.jar"]