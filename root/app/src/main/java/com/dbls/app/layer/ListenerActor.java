package com.dbls.app.layer;

import akka.actor.Cancellable;
import akka.actor.typed.*;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.dbls.app.layer.manager.DataProcessorInfoMessage;
import com.dbls.app.layer.manager.NewBlockMessage;
import com.dbls.app.layer.manager.SmartContractLogMessage;
import com.dbls.app.layer.message.*;
import com.dbls.app.layer.subscriber.BlockchainSubscriber;
import com.dbls.app.layer.subscriber.impl.BlockSubscriber;
import com.dbls.app.layer.subscriber.impl.SmartContractEventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.websocket.events.LogNotification;

import java.net.ConnectException;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Actor which listens blockchain node via web3j subscription
 * maps blocks/events into objects and sends them to data processing layer
 */

public class ListenerActor extends AbstractBehavior<Message> {

    private static final Logger LOG = LoggerFactory.getLogger(ListenerActor.class);
    private static final SupervisorStrategy strategy = SupervisorStrategy
            .restartWithBackoff(Duration.ofMillis(500L), Duration.ofSeconds(30L), 0.1)
            .withLoggingEnabled(false);

    private ActorRef<Message> dataProcessor;

    private Cancellable testRequestScheduler;
    private BlockchainSubscriber<EthBlock> blockSubscriber;
    private BlockchainSubscriber<LogNotification> smartContractEventSubscriber;

    public static Behavior<Message> create() {
        return Behaviors.supervise(Behaviors
                        .setup(ListenerActor::new))
                        .onFailure(strategy);
    }

    public ListenerActor(ActorContext<Message> context) {
        super(context);
        LOG.info("ListenerActor started");
        getContext().classicActorContext().parent().tell(new UpFromRestartMessage(getContext().getSelf()), null);
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
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(DataProcessorInfoMessage.class, this::onDataProcessorInfoMessage)
                .onMessage(TestAccesibilityMessage.class, this::sendTestMessage)
                .onSignal(PostStop.class, signal -> cleanUp())
                .onSignal(PreRestart.class, signal -> cleanUp())
                .build();
    }

    private Behavior<Message> onDataProcessorInfoMessage(DataProcessorInfoMessage message) throws ConnectException {
        LOG.info("New dataProcessor ref was accepted");
        this.dataProcessor = message.getDataProcessor();
        initSubscribers();
        testRequestScheduler = scheduleTestMessages();
        return this;
    }

    private void handleBlockSubscriberData(Object blockData) {
        if(blockData instanceof EthBlock) {
            LOG.info("Get new block with number: " + ((EthBlock) blockData).getBlock().getNumber());
            dataProcessor.tell(new NewBlockMessage((EthBlock) blockData));
        } else {
            LOG.warn("Invalid class for incoming block data: " + blockData.getClass());
        }
    }

    private void handleSmartContractEventSubscriberData(Object log) {
        if(log instanceof LogNotification) {
            dataProcessor.tell(new SmartContractLogMessage((LogNotification) log));
        } else {
            LOG.warn("Invalid class for incoming smart contract event data: " + log.getClass());
        }
    }

    private Behavior<Message> sendTestMessage(TestAccesibilityMessage message) throws ExecutionException, InterruptedException, TimeoutException {
        blockSubscriber.testAccesibility();
        return this;
    }

    private Cancellable scheduleTestMessages() {
        return getContext().classicActorContext().system().scheduler().scheduleAtFixedRate(Duration.ZERO,
                Duration.ofSeconds(5L),
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
