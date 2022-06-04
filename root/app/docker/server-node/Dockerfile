FROM anapsix/alpine-java
MAINTAINER valera
COPY app-1.0-SNAPSHOT-allinone.jar /home/node.jar
ENV PORT=2551
ENV ROLE=dataProcessor
EXPOSE $PORT
CMD java -jar /home/node.jar $ROLE