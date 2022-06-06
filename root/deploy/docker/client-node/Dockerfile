FROM anapsix/alpine-java
MAINTAINER valera
COPY client-1.0-SNAPSHOT-allinone.jar /home/node.jar
COPY client.config /home/client.config
ENV PORT=3551
ENV ROLE=searcher
ENV CFG_PATG=/home/client.config
EXPOSE $PORT
CMD java -jar /home/node.jar $CFG_PATG $ROLE