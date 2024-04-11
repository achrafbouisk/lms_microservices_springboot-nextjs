FROM openjdk:22
WORKDIR /course-service
ADD target/course-service-0.0.1-SNAPSHOT.jar course-service.jar
ENTRYPOINT [ "java", "-jar","course-service.jar" ]