FROM openjdk:11.0.9-buster
COPY build/libs/driver-service*.jar /deployments/driver-service*.jar
CMD java -jar /deployments/driver-service*.jar
