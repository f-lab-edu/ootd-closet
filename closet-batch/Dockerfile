FROM amazoncorretto:17 AS builder

WORKDIR /app

COPY gradlew ./
COPY gradle/ gradle/
COPY settings.gradle build.gradle ./

COPY closet-infra/ closet-infra/
COPY closet-batch/ closet-batch/
COPY closet-domain/ closet-domain/

RUN ./gradlew :closet-batch:bootJar -x test --no-daemon

# 런타임 스테이지
FROM amazoncorretto:17-alpine3.21

WORKDIR /app

ENV JVM_OPTS="-Duser.timezone=Asia/Seoul"
ENV SPRING_PROFILES_ACTIVE=prod

COPY --from=builder /app/closet-batch/build/libs/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

#  docker build -f closet-batch/Dockerfile -t closet-batch .
