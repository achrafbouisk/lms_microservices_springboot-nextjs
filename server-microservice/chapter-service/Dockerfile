FROM openjdk:22
WORKDIR /chapter-service
ADD target/chapter-service-0.0.1-SNAPSHOT.jar chapter-service.jar
ENTRYPOINT [ "java", "-jar","chapter-service.jar" ]