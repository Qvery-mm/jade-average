package ru.spbu.configuration;
import java.lang.Iterable;
public interface IAgentConfiguration {
    Iterable<String> getNeighborAgentAddresses(String agentId);
    int getNeighboursCount(String agentId);
    default void stop() throws InterruptedException {}
}
