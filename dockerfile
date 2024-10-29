FROM openjdk:17
ADD target/tpFoyer-17-0.0.1-SNAPSHOT.jar foyer.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "foyer.jar"]