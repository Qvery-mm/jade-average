package ru.spbu.average.behavior;


import com.google.gson.Gson;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ru.spbu.agent.DefaultAgent;
import ru.spbu.data.AgentInternalState;

import java.util.Queue;

public class StateBehavior extends CyclicBehaviour {

    private final DefaultAgent agent;

    public StateBehavior(DefaultAgent agent)
    {
        this.agent = agent;
    }
    @Override
    public void action() {
        ACLMessage msg = agent.receive();
        if (msg != null) {
            if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                ProcessAccept(msg);
            }
            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                ProcessProposal(msg);
            }
        }
        block();
    }

    private void ProcessAccept(ACLMessage msg) {
        String data = msg.getContent();
        Gson gson = new Gson();
        AgentInternalState neighbourInternalState  = gson.fromJson(data, AgentInternalState.class);

        float x = this.agent.getInternalState().getValue();
        float y = neighbourInternalState.getValue();
        int neighboursCount = agent.getConfiguration().getNeighboursCount(agent.getAgentId());

        Queue<Float> queue = agent.getInternalState().getQueue();
        queue.add(y);

//        System.out.printf("Queue length %s\n", queue.size());

        if (queue.size() < neighboursCount) {
            return;
        }

        float summary = 0;
        while (!queue.isEmpty()) {
            summary += (queue.remove() - x);
        }
        float z = x + 1f / (1 + neighboursCount) * summary;
        this.agent.getInternalState().setValue(z);
    }

    private void ProcessProposal(ACLMessage msg) {
        ACLMessage reply = msg.createReply();
        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        AgentInternalState agentInternalState = agent.getInternalState();
        Gson gson = new Gson();
        reply.setContent(gson.toJson(agentInternalState));
        agent.send(reply);
    }
}
