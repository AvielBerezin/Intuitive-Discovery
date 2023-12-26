package aviel.discovery.for_user;

import aviel.discovery.Discovery;
import aviel.discovery.DiscoveryRealizer;
import aviel.utils.NonThrowingClosable;

public abstract class EasyDiscovery<Listener> {
    public static EasySimpleDiscovery discovery() {
        return new EasySimpleDiscovery();
    }

    protected abstract Discovery<Listener> getBareDiscovery();

    public NonThrowingClosable realizeOnReaders(Listener listener) {
        return DiscoveryRealizer.realizeOnReaders(getBareDiscovery(), listener);
    }

    public NonThrowingClosable realizeOnWriters(Listener listener) {
        return DiscoveryRealizer.realizeOnWriters(getBareDiscovery(), listener);
    }
}
