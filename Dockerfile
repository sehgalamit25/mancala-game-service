##############################
## Build Application image
##############################

FROM openjdk:11

# Copy jar file
COPY target/kalah-0.0.1-SNAPSHOT.jar kalah.jar

#Expose port
EXPOSE 8080

#Entry Point
ENTRYPOINT ["java","-jar","kalah.jar"]
