package ru.spbu.configuration;
import java.lang.Iterable;
public interface IAgentConfiguration {
    public Iterable<String> getNeighborAgentAddresses(String agentId);
}
