package aviel.discovery;

import aviel.assumptions.DiscoveryData;
import aviel.assumptions.TopicData;

public interface SimpleListener {
    void onDiscovery(TopicData discoveredEntity);
    void onDisconnection(DiscoveryData disconnectedEntity);
}
