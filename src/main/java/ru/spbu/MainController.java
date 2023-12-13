package ru.spbu;
import java.util.*;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import ru.spbu.agent.DefaultAgent;
import ru.spbu.average.behavior.HeartBeatBehaviour;
import ru.spbu.average.behavior.StateBehavior;
import ru.spbu.configuration.ImmutableConfiguration;


public class MainController {
    private static final int SEED = 777;
    private static final int numberOfAgents = 5;
    public void initAgents() {
        // Retrieve the singleton instance of the JADE Runtime
        Runtime rt = Runtime.instance();
        Random random = new Random(SEED);
        //Create a container to host the Default Agent
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.MAIN_PORT, "10098");
        p.setParameter(Profile.GUI, "true");
        ContainerController cc = rt.createMainContainer(p);
        Map<String, Iterable<String>> internalGraph = new HashMap<>();

        // ring graph
        int total = MainController.numberOfAgents;
        for(int i = 1; i <= total; i++) {
            List<String> neighbours = new ArrayList<>();

            if (1 < i && i < total) {
                neighbours.add(String.valueOf(i+1));
                neighbours.add(String.valueOf(i-1));
            } else if (i == total) {
                neighbours.add(String.valueOf(1));
                neighbours.add(String.valueOf(total - 1));
            } else {
                neighbours.add(String.valueOf(i+1));
                neighbours.add(String.valueOf(total));
            }
            internalGraph.put(String.valueOf(i), neighbours);
        }

        List<AgentController> acList = new ArrayList<>();
        try {
            ImmutableConfiguration immutableConfiguration = new ImmutableConfiguration(internalGraph);
            for(int i = 1; i <= MainController.numberOfAgents; i++) {

                DefaultAgent agent = new DefaultAgent(String.valueOf(i), random);

                List<Behaviour> behaviors = new ArrayList<>() ;
                behaviors.add(new StateBehavior(agent));
                behaviors.add(new HeartBeatBehaviour(agent, 100));

                agent.setConfiguration(immutableConfiguration);
                agent.setBehaviours(behaviors);

                acList.add(cc.acceptNewAgent(Integer.toString(i), agent));

            }
            acList.forEach((ac) -> {
                try {
                    ac.start();
                } catch (StaleProxyException e) {
                    e.printStackTrace();
                    //throw new RuntimeException(e);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
