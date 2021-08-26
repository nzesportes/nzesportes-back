FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD build/libs/nzapi-0.0.1-SNAPSHOT.jar nzapi.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev","-jar","/nzapi.jar"]
