# see https://spring.io/guides/topicals/spring-boot-docker/
FROM eclipse-temurin:17-jdk-focal
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
