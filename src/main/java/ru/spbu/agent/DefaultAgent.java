package ru.spbu.agent;


import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SimpleBehaviour;
import ru.spbu.configuration.IAgentConfiguration;
import ru.spbu.data.AgentInternalState;

import java.lang.Iterable;
import java.util.Random;

public class DefaultAgent extends Agent implements IAgent {
    private IAgentConfiguration configuration;
    private Iterable<Behaviour> behaviours;
    private final String agentId;
    private AgentInternalState internalState;

    public DefaultAgent(String agentId, Random random) {
        this.internalState = new AgentInternalState(random);
        this.agentId = agentId;
    }
    @Override
    protected void setup() {
        getAID().setLocalName(agentId);
        System.out.printf("Agent #%s; value = %s%n", agentId, internalState.getValue());
        for(Behaviour behaviour : behaviours) {
            addBehaviour(behaviour);
        }
    }

    @Override
    protected void takeDown() {
        System.out.printf("Agent #%s; Result = %s%n", agentId, internalState.getValue());
    }

    public IAgentConfiguration getConfiguration(){
        return  configuration;
    }
    public void setConfiguration(IAgentConfiguration configuration)
    {
        this.configuration = configuration;
    }

    public Iterable<Behaviour> getBehaviors() {
        return behaviours;
    }

    public void setBehaviours(Iterable<Behaviour> behaviours) {
        this.behaviours = behaviours;
    }

    @Override
    public AgentInternalState getInternalState() {
        return internalState;
    }

    @Override
    public void setConfiguration(AgentInternalState internalState) {
        this.internalState = internalState;
    }
}
