package ru.spbu.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImmutableConfiguration implements IAgentConfiguration{
    private Map<String,Iterable<String>> neighbourAddresses;
    public ImmutableConfiguration(Map<String, Iterable<String>> neighbourAddresses)
    {
        this.neighbourAddresses = neighbourAddresses;
    }
    public Iterable<String> getNeighborAgentAddresses(String agentId) {
        return neighbourAddresses.get(agentId);
    }
}
