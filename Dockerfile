FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
COPY build/libs/nzapi-0.0.1-SNAPSHOT.jar nzapi.jar
COPY keystore.p12 keystore.p12
RUN echo 'hosts: files mdns4_minimal [NOTFOUND=return] dns mdns4' >> /etc/nsswitch.conf
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/nzapi.jar"]
