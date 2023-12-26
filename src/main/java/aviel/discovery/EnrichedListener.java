package aviel.discovery;

import aviel.assumptions.TopicData;

public interface EnrichedListener {
    void onDiscovery(TopicData discoveredEntity);
    void onDisconnection(TopicData disconnectedEntity);
}
