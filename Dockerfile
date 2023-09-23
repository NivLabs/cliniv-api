FROM openjdk:17
ADD target/cliniv-api.jar cliniv-api.jar
ENV DB_BASE=jdbc:mariadb://localhost:3306
ENV DB_USER=root
ENV DB_PASSWORD=root
ENV JWT_SECRET=U*Nu@$%%¨N!
ENV EMAIL_DEFAULT_SENDER=
ENV SMTP_HOST=
ENV SMTP_PORT=
ENV SMTP_MAIL_USERNAME=
ENV SMTP_MAIL_PASSWORD=
EXPOSE 	8081
ENTRYPOINT ["java", "-jar", "cliniv-api.jar"]