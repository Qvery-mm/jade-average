package ru.spbu.configuration;

import ru.spbu.topology.Node;
import ru.spbu.topology.RandomLinkedGraph;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DynamicConfiguration implements IAgentConfiguration {
    private final int nodeCount;
    private final int edgeCont;

    private volatile RandomLinkedGraph graph;

    private volatile Map<String, Set<String>> neighbourAddresses = new HashMap<>();
    private volatile Map<String, Integer> neighboursCounts = new HashMap<>();

    private final AtomicBoolean shutdown = new AtomicBoolean(false);

    public DynamicConfiguration(int nodeCount, int edgeCount) {
        this.nodeCount = nodeCount;
        this.edgeCont = edgeCount;
        this.graph = new RandomLinkedGraph(nodeCount, edgeCount, 777);
        update();
    }

    public void start() throws InterruptedException {
        Thread lifecycle = new Thread() {
            public void run() {
                try {
                    while (!shutdown.get()) {
                        update();
                        System.out.println(neighbourAddresses);
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        lifecycle.start();
    }

    private synchronized void update() {
            graph.create();
            neighbourAddresses = new HashMap<>();
            neighboursCounts = new HashMap<>();

            for (int i = 0; i < nodeCount; i++) {
                Node node = graph.getNodes().get(i);
                String name = String.valueOf(i);
                neighbourAddresses.computeIfAbsent(name, (__) -> new HashSet<>())
                        .addAll(node.getInputs().stream().map(String::valueOf).collect(Collectors.toSet()));
                neighboursCounts.computeIfAbsent(name, (__) -> node.getInputs().size());
            }
    }

    @Override
    public void stop() {
        shutdown.set(true);
    }

    @Override
    public synchronized Iterable<String> getNeighborAgentAddresses(String agentId) {
        return neighbourAddresses.get(agentId);
    }

    @Override
    public synchronized int getNeighboursCount(String agentId) {
        return neighboursCounts.get(agentId);
    }
}
