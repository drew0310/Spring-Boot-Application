FROM openjdk:21
ADD target/delds.jar delds.jar
ENTRYPOINT ["java","-jar","/delds.jar"]