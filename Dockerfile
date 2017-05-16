FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/mds.jar /mds/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/mds/app.jar"]
