package aviel.discovery.for_user;

import aviel.assumptions.TopicData;
import aviel.discovery.*;

import java.util.function.Predicate;

public class EasySmartTopicDiscovery extends EasyDiscovery<EnrichedListener> {
    private final Predicate<TopicData> filter;
    private final Discovery<EnrichedListener> discovery;

    public EasySmartTopicDiscovery(Predicate<TopicData> filter) {
        this.filter = filter;
        discovery = new Discovery<>() {
            @Override
            public <Basic> DiscoveryInitiator<Basic, EnrichedListener> fromSimple(DiscoveryInitiator<Basic, SimpleListener> initiator) {
                return InitiatorTranslators.smartTopicFromSimple(filter, initiator);
            }
        };
    }

    @Override
    protected Discovery<EnrichedListener> getBareDiscovery() {
        return discovery;
    }
}
