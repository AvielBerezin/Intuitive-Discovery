package aviel.discovery;

import aviel.assumptions.DiscoveryData;
import aviel.assumptions.TopicData;

import java.util.*;
import java.util.function.Predicate;

public class InitiatorTranslators {
    private record TopicLocatorInDomain(String partition, String topicName) {}

    public static <Basis> DiscoveryInitiator<Basis, StupidListener> topicFromSimple(DiscoveryInitiator<Basis, SimpleListener> discovery) {
        return stupidListener -> {
            Set<TopicLocatorInDomain> discovered = new HashSet<>();
            return discovery.initiate(new SimpleListener() {
                @Override
                public void onDiscovery(TopicData discoveredEntity) {
                    TopicLocatorInDomain topicLocator = new TopicLocatorInDomain(discoveredEntity.partition(), discoveredEntity.topicName());
                    synchronized (discovered) {
                        if (discovered.add(topicLocator)) {
                            stupidListener.onDiscovery(discoveredEntity);
                        }
                    }
                }

                @Override
                public void onDisconnection(DiscoveryData disconnectedEntity) {}
            });
        };
    }

    public static <Basis> DiscoveryInitiator<Basis, EnrichedListener> smartFromSimple(Predicate<TopicData> filter,
                                                                                      DiscoveryInitiator<Basis, SimpleListener> discovery) {
        return richListener -> {
            Map<DiscoveryData, TopicData> data = new HashMap<>();
            return discovery.initiate(new SimpleListener() {
                @Override
                public void onDiscovery(TopicData discoveredEntity) {
                    boolean filteredIn;
                    synchronized (data) {
                        filteredIn = filter.test(discoveredEntity);
                        if (filteredIn) {
                            data.put(discoveredEntity.discoveryData(), discoveredEntity);
                        }
                    }
                    if (filteredIn) {
                        richListener.onDiscovery(discoveredEntity);
                    }
                }

                @Override
                public void onDisconnection(DiscoveryData disconnectedEntity) {
                    Optional<TopicData> disconnected;
                    synchronized (data) {
                        disconnected = Optional.ofNullable(data.remove(disconnectedEntity));
                    }
                    disconnected.ifPresent(richListener::onDisconnection);
                }
            });
        };
    }

    public static <Basis> DiscoveryInitiator<Basis, EnrichedListener> smartTopicFromSmart(DiscoveryInitiator<Basis, EnrichedListener> discovery) {
        return richListener -> {
            Map<DiscoveryData, Integer> populationCount = new HashMap<>();
            return discovery.initiate(new EnrichedListener() {
                @Override
                public void onDiscovery(TopicData discoveredEntity) {
                    boolean firstAppearance;
                    synchronized (populationCount) {
                        firstAppearance = populationCount.putIfAbsent(discoveredEntity.discoveryData(), 0) == null;
                        populationCount.put(discoveredEntity.discoveryData(),
                                            populationCount.get(discoveredEntity.discoveryData()) + 1);
                    }
                    if (firstAppearance) {
                        richListener.onDiscovery(discoveredEntity);
                    }
                }

                @Override
                public void onDisconnection(TopicData disconnectedEntity) {
                    boolean unpopulated;
                    synchronized (populationCount) {
                        populationCount.put(disconnectedEntity.discoveryData(),
                                            populationCount.get(disconnectedEntity.discoveryData()) - 1);
                        unpopulated = populationCount.get(disconnectedEntity.discoveryData()) == 0;
                    }
                    if (unpopulated) {
                        richListener.onDisconnection(disconnectedEntity);
                    }
                }
            });
        };
    }

    public static <Basis> DiscoveryInitiator<Basis, EnrichedListener> smartTopicFromSimple(Predicate<TopicData> filter,
                                                                                           DiscoveryInitiator<Basis, SimpleListener> discovery) {
        return smartTopicFromSmart(smartFromSimple(filter, discovery));
    }
}
