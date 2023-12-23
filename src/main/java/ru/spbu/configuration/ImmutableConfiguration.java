package ru.spbu.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImmutableConfiguration implements IAgentConfiguration{
    private final Map<String, Iterable<String>> neighbourAddresses;
    private final Map<String, Integer> neighboursCounts = new HashMap<>();
    public ImmutableConfiguration(Map<String, Iterable<String>> neighbourAddresses) {
        this.neighbourAddresses = neighbourAddresses;

        neighbourAddresses.forEach((source, targets) ->
            targets.forEach(target -> neighboursCounts.put(
                    target, neighboursCounts.getOrDefault(target, 0) + 1))
        );
    }
    @Override
    public Iterable<String> getNeighborAgentAddresses(String agentId) {
        return neighbourAddresses.get(agentId);
    }

    @Override
    public int getNeighboursCount(String agentId) {
//        System.out.printf("Count %s\n", neighboursCounts.get(agentId));
        return neighboursCounts.get(agentId);
    }

}
