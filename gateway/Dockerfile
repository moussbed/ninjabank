FROM openjdk:17-alpine

EXPOSE 8080

RUN mkdir -p /usr/app

COPY ./target/gateway-*.jar /usr/app

WORKDIR /usr/app

CMD java -Djava.security.egd=file:/dev/./urandom -jar gateway-*.jar



