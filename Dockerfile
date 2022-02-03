#################################################################
####################### BUILD STAGE #############################
#################################################################
FROM gradle:6.8.3-jdk11 as builder

WORKDIR /opt/messenger/app-build/

# Copy gradle settings
COPY    build.gradle ./

# Copy project sources
COPY    src ./src

# Build jar
RUN     gradle jar

##################################################################
######################## TARGET STAGE ############################
##################################################################
# Use the image version used on the build stage
FROM adoptopenjdk:11-jdk-openj9

# Set unified timezone
ENV     TZ Europe/Moscow

# Copy built jar
COPY    --from=builder --chown=root /opt/messenger/app-build/build/libs/*.jar /opt/messenger/lib/

# Copy launcher and some additional files if needed
COPY    --chown=root run.sh /opt/messenger

# Set env vars to configure app launch
ENV     CPU_CORES 1
ENV     JAVA_XMS 128m
ENV     JAVA_XMX 256m

EXPOSE 8080

CMD ["/opt/messenger/run.sh"]
