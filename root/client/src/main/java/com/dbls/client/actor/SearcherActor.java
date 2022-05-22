package com.dbls.client.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.dbls.client.message.NewWorkRequest;
import com.dbls.client.message.NewWorkResponse;
import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import com.dbls.client.service.SearcherService;
import com.dbls.client.service.configuration.RabbitMQConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearcherActor extends AbstractActor {

    private static final Logger LOG = LoggerFactory.getLogger(SearcherActor.class);

    private SearcherService searcherService;
    private ActorRef balancer;

    public static Props props(ActorRef actorRef) {
        return Props.create(SearcherActor.class, actorRef);
    }

    public SearcherActor(ActorRef balancer) {
        super();
        this.searcherService = new SearcherService();
        this.balancer = balancer;
        LOG.info("Searcher was started");
    }

    @Override
    public void preStart() {
        LOG.info("Send first request");
        balancer.tell(new NewWorkRequest(null), getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(NewWorkResponse.class, this::handleNewWorkResponse)
                .build();
    }

    private void handleNewWorkResponse(NewWorkResponse msg) throws JsonProcessingException {
        AmqpRequest amqpRequest = msg.getAmqpRequest();
        if(amqpRequest == null) {
            sendNewWorkRequest(null);
            return;
        }
        LOG.info("New work response with AmqpRequest was received");
        sendNewWorkRequest(searcherService.executeQuery(msg.getAmqpRequest()));
    }

    private void sendNewWorkRequest(AmqpResponse amqpResponse) {
        balancer.tell(new NewWorkRequest(amqpResponse), getSelf());
    }
}
