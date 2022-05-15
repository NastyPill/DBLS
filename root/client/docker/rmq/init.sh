#!/bin/sh

# Create Rabbitmq user
( sleep 5 ; \
rabbitmqctl add_user "$RABBITMQ_USER" "$RABBITMQ_PASSWORD" 2>/dev/null ; \
rabbitmqctl set_user_tags "$RABBITMQ_USER" administrator ; \
rabbitmqctl set_permissions -p / "$RABBITMQ_USER"  ".*" ".*" ".*" ; \
echo "*** User '$RABBITMQ_USER' with password '$RABBITMQ_PASSWORD' completed. ***" ; \
rabbitmqctl add_vhost Some_Virtual_Host ; \
rabbitmqctl set_permissions -p Some_Virtual_Host guest ".*" ".*" ".*" ; \
echo "*** Log in the WebUI at port 15672 (example: http:/localhost:15672) ***") &

rabbitmq-server

rabbitmqadmin -u "$RABBITMQ_USER" -p "$RABBITMQ_PASSWORD" -V / declare queue name=test.queue.in
rabbitmqadmin -u "$RABBITMQ_USER" -p "$RABBITMQ_PASSWORD" -V / declare queue name=test.queue.out