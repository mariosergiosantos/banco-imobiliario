FROM openjdk:21-jdk-slim

RUN addgroup --system spring && adduser --system --ingroup spring spring \
    && mkdir -p /home/spring \
    && chown -R spring:spring /home/spring

WORKDIR /home/spring

COPY target/*.jar app.jar

RUN chown spring:spring /home/spring/app.jar

USER spring:spring

EXPOSE 8080

ENV AWS_ACCESS_KEY_ID=test \
    AWS_SECRET_ACCESS_KEY=test \
    AWS_REGION=us-east-1

CMD ["java", "-jar", "/home/spring/app.jar"]
