FROM openjdk:17
ADD target/cliniv-api.jar cliniv-api.jar
EXPOSE 	8080
ENTRYPOINT ["java", "-jar", "cliniv-api.jar"]