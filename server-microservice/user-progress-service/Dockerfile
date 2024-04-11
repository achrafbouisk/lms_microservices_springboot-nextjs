FROM openjdk:22
WORKDIR /user-progress-service
ADD target/user-progress-service-0.0.1-SNAPSHOT.jar user-progress-service.jar
ENTRYPOINT [ "java", "-jar","user-progress-service.jar" ]