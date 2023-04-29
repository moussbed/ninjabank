FROM openjdk:17-alpine

EXPOSE 8000

RUN mkdir -p /usr/app

COPY ./target/backend-*.jar /usr/app

WORKDIR /usr/app

CMD java -Djava.security.egd=file:/dev/./urandom -jar backend-*.jar



