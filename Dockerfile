FROM openjdk:17 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:17
WORKDIR /springboot_ecommerce
COPY --from=build target/*.jar springboot_ecommerce.jar
ENTRYPOINT ["java", "-jar", "springboot_ecommerce.jar"]
