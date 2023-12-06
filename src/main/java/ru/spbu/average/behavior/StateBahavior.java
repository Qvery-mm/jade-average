package ru.spbu.average.behavior;


import com.google.gson.Gson;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ru.spbu.agent.DefaultAgent;
import ru.spbu.data.AgentInternalState;

public class StateBahavior extends CyclicBehaviour {

    private final DefaultAgent agent;

    public StateBahavior(DefaultAgent agent)
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
        //ToDo: process neighbourInternalState, e.g calculate average
        float x = this.agent.getInternalState().getValue();
        float y = neighbourInternalState.getValue();

        float z = 1f / 2 * (y - x);
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
