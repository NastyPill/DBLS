package com.dbls.client.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
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

    public static Props props() {
        return Props.create(SearcherActor.class);
    }

    public SearcherActor() {
        super();
        this.searcherService = new SearcherService();
        ActorSystem system = getContext().system();
        ClusterSingletonProxySettings proxySettings =
                ClusterSingletonProxySettings.create(system);
        this.balancer = system.actorOf(ClusterSingletonProxy.props("/user/balancer", proxySettings), "balancerProxy");
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
