package aviel.assumptions;

import aviel.discovery.SimpleListener;

public record DomainParticipant(DomainParticipantParams params) {
    public void startReadersDiscovery(SimpleListener listener) {}

    public void startWritersDiscovery(SimpleListener listener) {}

    public void close() {}
}
