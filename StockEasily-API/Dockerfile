FROM openjdk:17-jdk-slim
RUN mkdir /opt/app
COPY ./build/libs/StockEasily-API-0.0.1-SNAPSHOT.jar /opt/app
EXPOSE 8080
CMD ["java", "-jar", "StockEasily-API-0.0.1-SNAPSHOT.jar"]
