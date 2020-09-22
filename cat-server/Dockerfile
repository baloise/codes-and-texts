FROM openjdk:8-jre-alpine
# RUN addgroup spring && adduser spring --ingroup spring
# USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]