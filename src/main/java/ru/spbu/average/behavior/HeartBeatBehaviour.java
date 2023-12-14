package ru.spbu.average.behavior;

import java.lang.Iterable;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import ru.spbu.agent.DefaultAgent;

public class HeartBeatBehaviour extends TickerBehaviour {
    private final DefaultAgent agent;
    private int currentStep;
    private final int MAX_STEPS = 50;
    public HeartBeatBehaviour(DefaultAgent agent, long period) {
        super(agent, period);
        this.setFixedPeriod(true);
        this.agent = agent;
        this.currentStep = 0;
    }
    @Override
    protected void onTick() {
        if (currentStep < MAX_STEPS) {
            System.out.println("Agent " + this.agent.getLocalName() + ": tick=" + getTickCount());
            SendBroadcastMessage();
            this.currentStep++;
            block();
        } else {
            System.out.printf("Agent %s; Result = %s\n", agent.getAgentId(), agent.getInternalState().getValue());
            try {
                agent.getConfiguration().stop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.stop();
        }
    }

    private void SendBroadcastMessage()
    {
        ACLMessage msg = new ACLMessage();
        msg.addReplyTo(agent.getAID());
        msg.setPerformative(ACLMessage.PROPOSE);
        msg.setContent("");
        Iterable<String> neighbours = agent.getConfiguration().getNeighborAgentAddresses(agent.getAgentId());
        for(String neighbour : neighbours) {
            msg.addReceiver(new AID(neighbour, false ));
        }
        agent.send(msg);
    }
}
