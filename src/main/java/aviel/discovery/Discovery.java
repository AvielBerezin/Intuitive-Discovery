package aviel.discovery;

import aviel.utils.NonThrowingClosable;

public interface Discovery<Listener> {
    <Basic> DiscoveryInitiator<Basic, Listener> fromSimple(DiscoveryInitiator<Basic, SimpleListener> initiator);

    default NonThrowingClosable realizeOnReaders(Listener listener) {
        return DiscoveryRealizer.realizeOnReaders(this, listener);
    }

    default NonThrowingClosable realizeOnWriters(Listener listener) {
        return DiscoveryRealizer.realizeOnWriters(this, listener);
    }
}
