FROM openjdk:17-alpine

EXPOSE 8081

RUN mkdir -p /usr/app

COPY ./target/security-*.jar /usr/app

WORKDIR /usr/app

CMD java -Djava.security.egd=file:/dev/./urandom  -jar security-*.jar
