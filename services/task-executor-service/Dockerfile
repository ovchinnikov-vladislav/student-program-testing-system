FROM ubuntu:14.04
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
RUN sudo apt-get install -y --no-install-recommends software-properties-common
RUN sudo add-apt-repository -y ppa:openjdk-r/ppa
RUN sudo apt-get update
RUN sudo apt-get install -y openjdk-12-jdk
RUN sudo apt-get install -y openjdk-12-jre
RUN sudo update-alternatives --config java
RUN sudo update-alternatives --config javac
ENTRYPOINT ["java","-jar","/app.jar"]
