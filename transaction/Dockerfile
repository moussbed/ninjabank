FROM openjdk:17-alpine

EXPOSE 8081

RUN mkdir -p /usr/app

COPY ./target/transaction-*.jar /usr/app

WORKDIR /usr/app

CMD java -Djava.security.egd=file:/dev/./urandom -jar transaction-*.jar



