FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./build/libs/content-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/content-service.jar"]

EXPOSE 3000