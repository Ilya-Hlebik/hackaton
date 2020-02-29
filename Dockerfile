FROM openjdk:11
VOLUME /tmp
MAINTAINER java@ilyaHlebik.com
COPY backend/target/backend-0.0.1-SNAPSHOT.jar opt/hackathon.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar","opt/hackathon.jar"]