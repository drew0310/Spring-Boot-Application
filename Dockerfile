FROM openjdk:21
ADD delds/target/delds.jar delds.jar
ENTRYPOINT ["java","-jar","/delds.jar"]