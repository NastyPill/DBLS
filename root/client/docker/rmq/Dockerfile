FROM rabbitmq:management

ENV RABBITMQ_USER user
ENV RABBITMQ_PASSWORD pass

ADD init.sh /init.sh
ADD definitions.json /etc/rabbitmq
ADD rabbitmq.conf /etc/rabbitmq
EXPOSE 15672

CMD ["/init.sh"]