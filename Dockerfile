FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src

ENV BASE_URL=https://www.saucedemo.com
ENV BROWSER=chrome

# Shell form (not exec/JSON-array form) — exec form doesn't invoke a shell,
# so ${BROWSER} would never actually be expanded and mvn would receive the
# literal string "-Dbrowser=${BROWSER}" instead of the real browser name.
# hadolint ignore=DL3025
CMD mvn test -q -Dbrowser=${BROWSER} -Dheadless=true
