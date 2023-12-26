package aviel.discovery.for_user;

import aviel.assumptions.TopicData;
import aviel.discovery.*;

import java.util.function.Predicate;

public class EasySmartDiscovery extends EasyDiscovery<EnrichedListener> {
    private final Discovery<EnrichedListener> discovery;
    private final Predicate<TopicData> filter;

    public EasySmartDiscovery(Predicate<TopicData> filter) {
        this.filter = filter;
        discovery = new Discovery<>() {
            @Override
            public <Basic> DiscoveryInitiator<Basic, EnrichedListener> fromSimple(DiscoveryInitiator<Basic, SimpleListener> initiator) {
                return InitiatorTranslators.smartFromSimple(filter, initiator);
            }
        };
    }

    @Override
    protected Discovery<EnrichedListener> getBareDiscovery() {
        return discovery;
    }

    public EasySmartTopicDiscovery perTopic() {
        return new EasySmartTopicDiscovery(filter);
    }
}
