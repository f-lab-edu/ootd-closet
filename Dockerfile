# 빌드 스테이지
FROM amazoncorretto:17 AS builder

WORKDIR /app

# gradle 래퍼 + 설정 복사
COPY gradlew .
COPY gradle/ gradle/
COPY settings.gradle build.gradle ./

COPY closet-api/ closet-api/
COPY closet-domain/ closet-domain/

RUN chmod +x gradlew
RUN ./gradlew :closet-api:bootJar -x test --no-daemon

# 런타임 스테이지
FROM amazoncorretto:17-alpine3.21

WORKDIR /app

ENV JVM_OPTS="-Duser.timezone=Asia/Seoul"

COPY --from=builder /app/closet-api/build/libs/*.jar app.jar

EXPOSE 80

ENTRYPOINT ["sh", "-c", "java ${JVM_OPTS} -jar app.jar"]
