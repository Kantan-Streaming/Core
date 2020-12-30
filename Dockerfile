FROM adoptopenjdk:11-jdk-hotspot

COPY target/core-0.0.1-SNAPSHOT.jar /backend.jar

EXPOSE 80

ENTRYPOINT ["java", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "/backend.jar", \
  "--spring.profiles.active=production"]