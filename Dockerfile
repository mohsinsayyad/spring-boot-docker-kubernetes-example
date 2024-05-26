FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /

COPY target/tasks-0.0.1-SNAPSHOT.jar tasks-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","tasks-0.0.1-SNAPSHOT.jar"]