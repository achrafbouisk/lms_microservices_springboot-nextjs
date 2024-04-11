FROM openjdk:22
WORKDIR /service-registry
ADD target/service-registry-0.0.1-SNAPSHOT.jar service-registry.jar
ENTRYPOINT [ "java", "-jar","service-registry.jar" ]