# For Java 8, try this
 FROM openjdk:8-jdk-alpine

# Refer to Maven build -> finalName
ARG JAR_FILE=target/com.ytulink.category-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/com.ytulink.category-0.0.1-SNAPSHOT.jar /opt/app/category.jar
COPY ${JAR_FILE} category.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","category.jar"]