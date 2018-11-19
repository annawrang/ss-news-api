# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Where spring boot creates working directories for Tomcat by default
VOLUME /tmp

# copy application WAR (with libraries inside)
COPY target/ss-news-api-0.0.1-SNAPSHOT.jar /app.jar

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
