FROM openjdk:17
ADD target/tpFoyer-17-0.0.1-SNAPSHOT.jar foyer.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "foyer.jar"]