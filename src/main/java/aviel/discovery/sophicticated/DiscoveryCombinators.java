package aviel.discovery.sophicticated;

import aviel.discovery.Discovery;
import aviel.discovery.DiscoveryInitiator;
import aviel.discovery.SimpleListener;

public class DiscoveryCombinators {
    public static <Listener> Discovery<Listener> sequence(Discovery<Listener> discovery1,
                                                          Discovery<Listener> discovery2) {
        return new Discovery<>() {
            @Override
            public <Basic> DiscoveryInitiator<Basic, Listener> fromSimple(DiscoveryInitiator<Basic, SimpleListener> initiator) {
                return listener ->
                        ;
            }
        };
    }
}
