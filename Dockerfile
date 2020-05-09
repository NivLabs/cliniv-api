FROM openjdk:11
ADD target/gestao-de-prontuario.jar gestao-de-prontuario.jar
EXPOSE 	8080
ENTRYPOINT ["java", "-jar", "gestao-de-prontuario.jar"]