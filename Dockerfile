FROM openjdk:17 as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

FROM openjdk:17
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ARG DEPENDENCY=/workspace/app/target
COPY --from=build ${DEPENDENCY}/photos-downloader-0.0.1-SNAPSHOT.jar photosdownloader.jar
ENTRYPOINT exec java $JAVA_OPTS -jar photosdownloader.jar -d /tmp
