FROM openjdk:8-jdk-alpine
RUN mkdir -p /papers

COPY  /target/DISS-0.1-1.0-SNAPSHOT.jar SocialStreamParserApp.jar
EXPOSE 80
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/SocialStreamParserApp.jar"]

