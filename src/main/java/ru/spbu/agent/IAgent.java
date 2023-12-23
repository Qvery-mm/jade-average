package ru.spbu.agent;
import java.lang.Iterable;

import jade.core.behaviours.Behaviour;
import ru.spbu.configuration.IAgentConfiguration;
import ru.spbu.data.AgentInternalState;

public interface IAgent {
    public IAgentConfiguration getConfiguration();
    public void setConfiguration(IAgentConfiguration configuration);
    public Iterable<Behaviour> getBehaviors();
    public void setBehaviours(Iterable<Behaviour> behaviors);

    public AgentInternalState getInternalState();
    public void setConfiguration(AgentInternalState internalState);
}
