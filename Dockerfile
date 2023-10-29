FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/BirdFarmShop-0.0.1-SNAPSHOT.jar BirdFarmShop.jar
ENTRYPOINT ["java","-jar","/BirdFarmShop.jar"]
EXPOSE 8080