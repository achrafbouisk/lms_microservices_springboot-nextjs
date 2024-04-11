FROM openjdk:22
WORKDIR /api-gateway
ADD target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
ENTRYPOINT [ "java", "-jar","api-gateway.jar" ]