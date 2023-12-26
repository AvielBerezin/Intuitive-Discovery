package aviel.discovery.for_user;

import aviel.discovery.*;

public class EasyTopicDiscovery extends EasyDiscovery<StupidListener> {
    private final Discovery<StupidListener> discovery;

    public EasyTopicDiscovery() {
        discovery = InitiatorTranslators::topicFromSimple;
    }

    @Override
    protected Discovery<StupidListener> getBareDiscovery() {
        return discovery;
    }
}
