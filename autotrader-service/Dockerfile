FROM amazoncorretto:11-alpine


# Create user:group -> spring:spring
RUN addgroup -S spring && adduser -S spring -G spring

# Move to workspace as 'spring' user owner
RUN mkdir -p /opt/app/
WORKDIR /opt/app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} service.jar
RUN chown -R spring:spring /opt/app

# We now use 'spring' user
USER spring:spring

# Execute project
ENTRYPOINT ["java","-jar","/opt/app/service.jar"]
