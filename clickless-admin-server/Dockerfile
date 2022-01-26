FROM openjdk:8-jdk

RUN mkdir /app

COPY ./target/clickless-admin-server.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
