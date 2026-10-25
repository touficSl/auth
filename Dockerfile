# ---- Build stage ----
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
# run as an unprivileged user
RUN groupadd --system app && useradd --system --gid app --no-create-home app
COPY --from=build /build/target/auth-*.war /app/auth.war
USER app
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/auth.war"]
