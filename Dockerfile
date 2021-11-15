FROM openjdk:11
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

#docker build -t tms/myimage .
#docker run -d -p 8080:8080 tms/myimage
#docker images
#docker ps
#docker stop <ID CONTAINER>
#docker rm <ID CONTAINER>
#docker rm images <IMAGE TAG>
