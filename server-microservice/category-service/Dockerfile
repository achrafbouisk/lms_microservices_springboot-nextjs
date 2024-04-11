FROM openjdk:22
WORKDIR /category-service
ADD target/category-service-0.0.1-SNAPSHOT.jar category-service.jar
ENTRYPOINT [ "java", "-jar","category-service.jar" ]