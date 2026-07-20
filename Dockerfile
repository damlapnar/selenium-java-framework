FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src

ENV BASE_URL=https://www.saucedemo.com
ENV BROWSER=chrome

CMD ["mvn", "test", "-q", "-Dbrowser=${BROWSER}"]
