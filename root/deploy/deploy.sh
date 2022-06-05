#!/bin/bash

cd ../app/docker/server-node
docker build -t server .

cd ../../../client/docker/client-node
docker build -t client .
cd ../rmq
docker build -t rmq .
cd ../../../rest/docker/rest
docker build -t rest .

docker run -d --net="host" --name="rmq" rmq
docker run -d --net="host" --name="dataProcessor" -e "PORT=2551" -e "ROLE=dataProcessor" server
docker run -d --net="host" --name="listener1" -e "PORT=2555" -e "ROLE=listener" server
docker run -d --net="host" --name="listener2" -e "PORT=2556" -e "ROLE=listener" server
sleep 5
docker restart rmq
sleep 10
docker run -d --net="host" --name="balancer" -e "PORT=3551" -e "ROLE=balancer" client
docker run -d --net="host" --name="searcher1" -e "PORT=3555" -e "ROLE=searcher" client
docker run -d --net="host" --name="searcher2" -e "PORT=3556" -e "ROLE=searcher" client
docker run -d --net="host" --name="rest" rest


