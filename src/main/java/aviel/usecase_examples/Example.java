package aviel.usecase_examples;

import aviel.discovery.for_user.EasyDiscovery;

public class Example {
    public static void main(String[] args) {
        EasyDiscovery.discovery()
                     .perTopic()
                     .realizeOnReaders(discoveredEntity -> {});
    }
}
