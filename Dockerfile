FROM openjdk:21
EXPOSE 8080
ADD target/bestcloudforme.jar bestcloudforme.jar
ENTRYPOINT ["java", "-jar", "/bestcloudforme.jar"]
