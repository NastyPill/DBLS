FROM rabbitmq:management

ENV RABBITMQ_USER user
ENV RABBITMQ_PASSWORD password

ADD init.sh /init.sh
EXPOSE 15672

CMD ["/init.sh"]