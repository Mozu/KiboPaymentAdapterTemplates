FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY @project.artifactId@-@project.version@.jar /@project.artifactId@.jar
ENV JAVA_OPTS=""
ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /@project.artifactId@.jar
