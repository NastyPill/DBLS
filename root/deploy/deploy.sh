#!/bin/bash

cd ../app/docker/server-node
docker build -t server .
cd ../postgresql
docker build -t postgres .
cd ../../../client/docker/client-node
docker build -t client .
cd ../rmq
docker build -t rmq .

docker create --net="host" --name="rmq" rmq
docker create --net="host" --name="postgres" postgres
docker create --net="host" --name="dataProcessor" -e "PORT=2551" -e "ROLE=dataProcessor" server
docker create --net="host" --name="listener1" -e "PORT=2555" -e "ROLE=listener" server
docker create --net="host" --name="listener2" -e "PORT=2556" -e "ROLE=listener" server
docker create --net="host" --name="balancer" -e "PORT=3551" -e "ROLE=balancer" client
docker create --net="host" --name="searcher1" -e "PORT=3555" -e "ROLE=searcher" client
docker create --net="host" --name="searcher2" -e "PORT=3556" -e "ROLE=searcher" client
