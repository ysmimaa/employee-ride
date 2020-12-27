FROM openjdk:11.0.9-buster

VOLUME /temp

COPY build/libs/driver-service*.jar /opt/driver-service_home/deployments/driver-service*.jar

CMD ["java" , "-jar" ,"/opt/driver-service_home/deployments/driver-service*.jar"]

EXPOSE 8000
