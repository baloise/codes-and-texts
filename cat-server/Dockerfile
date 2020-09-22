FROM bitnami/java:1.8
# RUN addgroup spring && adduser spring --ingroup spring
# USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]