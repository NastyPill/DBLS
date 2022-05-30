package com.dbls.app.layer;

import akka.actor.*;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import akka.japi.pf.DeciderBuilder;
import com.dbls.app.layer.message.NewBlockMessage;
import com.dbls.app.layer.message.SmartContractLogMessage;
import com.dbls.app.layer.message.*;
import com.dbls.app.layer.subscriber.BlockchainSubscriber;
import com.dbls.app.layer.subscriber.impl.BlockSubscriber;
import com.dbls.app.layer.subscriber.impl.SmartContractEventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.websocket.events.LogNotification;
import scala.concurrent.duration.Duration;

import java.net.ConnectException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Actor which listens blockchain node via web3j subscription
 * maps blocks/events into objects and sends them to data processing layer
 */

public class ListenerActor extends AbstractActor {

    private static final Logger LOG = LoggerFactory.getLogger(ListenerActor.class);
    private static final SupervisorStrategy strategy = new OneForOneStrategy(
            -1,
            Duration.Inf(),
            DeciderBuilder.matchAny(o -> SupervisorStrategy.restart())
                    .build());

    private ActorRef dataProcessor;

    private Cancellable testRequestScheduler;
    private BlockchainSubscriber<EthBlock> blockSubscriber;
    private BlockchainSubscriber<LogNotification> smartContractEventSubscriber;

    public static Props props() {
        return Props.create(ListenerActor.class);
    }

    public ListenerActor() {
        LOG.info("ListenerActor started");
    }

    private void initSubscribers() throws ConnectException {
        blockSubscriber = new BlockSubscriber();
        blockSubscriber.createSubscription(this::handleBlockSubscriberData);
        LOG.info("Transaction subscriber has subscribed");

        smartContractEventSubscriber = new SmartContractEventSubscriber();
        smartContractEventSubscriber.createSubscription(this::handleSmartContractEventSubscriberData);
        LOG.info("Smart Contract Event subscriber has subscribed");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TestAccesibilityMessage.class, this::sendTestMessage)
                .build();
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        initSubscribers();
        testRequestScheduler = scheduleTestMessages();
        ActorSystem system = context().system();
        ClusterSingletonProxySettings proxySettings =
                ClusterSingletonProxySettings.create(system);
        this.dataProcessor = system.actorOf(ClusterSingletonProxy.props("/user/dataProcessor", proxySettings), "dataProcessorProxy");

    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        cleanUp();
    }

    @Override
    public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
        super.preRestart(reason, message);
        cleanUp();
    }


    private void handleBlockSubscriberData(Object blockData) {
        if(blockData instanceof EthBlock) {
            LOG.info("Get new block with number: " + ((EthBlock) blockData).getBlock().getNumber());
            dataProcessor.tell(new NewBlockMessage((EthBlock) blockData), getSelf());
        } else {
            LOG.warn("Invalid class for incoming block data: " + blockData.getClass());
        }
    }

    private void handleSmartContractEventSubscriberData(Object log) {
        if(log instanceof LogNotification) {
            dataProcessor.tell(new SmartContractLogMessage((LogNotification) log), getSelf());
        } else {
            LOG.warn("Invalid class for incoming smart contract event data: " + log.getClass());
        }
    }

    private void sendTestMessage(TestAccesibilityMessage message) throws ExecutionException, InterruptedException, TimeoutException {
        blockSubscriber.testAccesibility();
    }

    private Cancellable scheduleTestMessages() {
        return getContext().classicActorContext().system().scheduler().scheduleAtFixedRate(Duration.Zero(),
                Duration.create(5L, TimeUnit.SECONDS),
                getContext().classicActorContext().self(),
                new TestAccesibilityMessage(),
                getContext().classicActorContext().dispatcher(),
                akka.actor.ActorRef.noSender());
    }

    private ListenerActor cleanUp() {
        LOG.info("Listener actor stopped");
        if(smartContractEventSubscriber != null) {
            smartContractEventSubscriber.onStop();
            LOG.info("Smart contract events subscription disposed");
        }
        if(blockSubscriber != null) {
            blockSubscriber.onStop();
            LOG.info("Blocks subscription disposed");
        }
        if(testRequestScheduler != null) {
            testRequestScheduler.cancel();
            LOG.info("Scheduling for test request was cancelled");
        }
        return this;
    }
}
