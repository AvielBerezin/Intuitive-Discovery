package aviel.discovery;

import aviel.assumptions.DomainParticipant;
import aviel.assumptions.DomainParticipantParams;
import aviel.utils.NonThrowingClosable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class DiscoveryRealizer {
    private static final AtomicInteger discoverersCount = new AtomicInteger();

    public static <Listener> NonThrowingClosable realizeOnReaders(Discovery<Listener> trans, Listener listener) {
        return realize(trans, listener, getInnerInitiator(DomainParticipant::startReadersDiscovery));
    }

    public static <Listener> NonThrowingClosable realizeOnWriters(Discovery<Listener> trans, Listener listener) {
        return realize(trans, listener, getInnerInitiator(DomainParticipant::startWritersDiscovery));
    }

    private static <Listener> NonThrowingClosable realize(Discovery<Listener> trans, Listener listener, DiscoveryInitiator<DomainParticipantParams, SimpleListener> innerWritersInitiator) {
        return trans.fromSimple(innerWritersInitiator)
                    .initiate(listener)
                    .apply(new DomainParticipantParams("discovery-" + discoverersCount.getAndIncrement()));
    }

    private static DiscoveryInitiator<DomainParticipantParams, SimpleListener> getInnerInitiator(BiConsumer<DomainParticipant, SimpleListener> listen) {
        return listener -> params -> {
            DomainParticipant domainParticipant = new DomainParticipant(params);
            listen.accept(domainParticipant, listener);
            return domainParticipant::close;
        };
    }
}
