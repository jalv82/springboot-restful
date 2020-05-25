# Java 14
FROM openjdk:14-alpine

# Refer to Maven build -> finalName
ARG JAR_FILE=target/restful-0.0.2.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/restful-0.0.2.jar
COPY ${JAR_FILE} restful-0.0.2.jar

# java -jar /opt/app/restful-0.0.2.jar
ENTRYPOINT ["java","-jar","restful-0.0.2.jar"]