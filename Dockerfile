FROM bellsoft/liberica-openjre-alpine:22 AS layers
WORKDIR /application
COPY target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM bellsoft/liberica-openjre-alpine:22
VOLUME /tmp
RUN adduser -S spring-user
USER spring-user
COPY --from=layers /application/dependencies/ ./
COPY --from=layers /application/spring-boot-loader/ ./
COPY --from=layers /application/snapshot-dependencies/ ./
COPY --from=layers /application/application/ ./
USER root
RUN mkdir -p /app && chmod 777 /app

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
