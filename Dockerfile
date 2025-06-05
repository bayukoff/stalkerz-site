FROM amazoncorretto:17-alpine
LABEL authors="Coolz"

WORKDIR /app

COPY build/libs/sector-backend.jar .

CMD ["java", "-jar", "/app/sector-backend.jar"]