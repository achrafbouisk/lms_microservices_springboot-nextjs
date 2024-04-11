FROM openjdk:22
WORKDIR /attachment-service
ADD target/attachment-service-0.0.1-SNAPSHOT.jar attachment-service.jar
ENTRYPOINT [ "java", "-jar","attachment-service.jar" ]