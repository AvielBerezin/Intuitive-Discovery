package aviel.discovery.for_user;

import aviel.assumptions.TopicData;
import aviel.discovery.Discovery;
import aviel.discovery.DiscoveryInitiator;
import aviel.discovery.SimpleListener;

import java.util.function.Predicate;

public class EasySimpleDiscovery extends EasyDiscovery<SimpleListener> {
    private final Discovery<SimpleListener> discovery;

    public EasySimpleDiscovery() {
        this.discovery = new Discovery<>() {
            @Override
            public <Basic> DiscoveryInitiator<Basic, SimpleListener> fromSimple(DiscoveryInitiator<Basic, SimpleListener> initiator) {
                return initiator;
            }
        };
    }

    @Override
    protected Discovery<SimpleListener> getBareDiscovery() {
        return discovery;
    }

    public EasyTopicDiscovery perTopic() {
        return new EasyTopicDiscovery();
    }

    public EasySmartDiscovery smart(Predicate<TopicData> filter) {
        return new EasySmartDiscovery(filter);
    }
}
