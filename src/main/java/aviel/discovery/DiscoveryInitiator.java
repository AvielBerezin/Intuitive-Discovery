package aviel.discovery;

import aviel.utils.NonThrowingClosable;

import java.util.function.Function;

@FunctionalInterface
public interface DiscoveryInitiator<Basis, Listener> {
    Function<Basis, NonThrowingClosable> initiate(Listener listener);
}
