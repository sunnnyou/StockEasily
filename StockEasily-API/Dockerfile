FROM openjdk:17-jdk-slim
RUN mkdir /opt/app
COPY ./build/libs/StockEasily-1.0-Snapshot.jar /opt/app
EXPOSE 8080
CMD ["java", "-jar", "StockEasily-1.0-Snapshot.jar"]
