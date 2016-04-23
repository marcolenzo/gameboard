FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD /gameboard/target/gameboard.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.config.location=/configs/application.properties"]
