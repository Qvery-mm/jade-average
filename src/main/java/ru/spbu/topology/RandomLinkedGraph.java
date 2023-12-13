package ru.spbu.topology;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomLinkedGraph implements IGraph {
    private final Random random;
    private final int nodeCount;
    private final int edgeCount;
    private final int seed;
    private List<Node> nodes;

    public RandomLinkedGraph(int nodeCount, int edgeCount, int seed) {
        assert (nodeCount > 1);
        assert (edgeCount >= 0);
        assert (edgeCount >= nodeCount - 1);
        assert (2 * edgeCount <= nodeCount * (nodeCount - 1));

        this.random = new Random(seed);
        this.nodeCount = nodeCount;
        this.edgeCount = edgeCount;
        this.seed = seed;
        this.nodes = new ArrayList<>(nodeCount);
    }

    @Override
    public void create() {
        RandomSpanningTree tree = new RandomSpanningTree(nodeCount, seed);
        tree.create();
        nodes = tree.getNodes();

        List<Pair> edges = new ArrayList<>((nodeCount * (nodeCount - 1)) / 2);
        for (int i = 0; i < nodeCount-1; i++) {
            for (int j = i+1; j < nodeCount; j++) {
                edges.add(new Pair(i, j));
            }
        }
        Collections.shuffle(edges, random);

        int totalEdges = nodeCount - 1;
        int i = 0;
        while (totalEdges < edgeCount) {
            Node first = nodes.get(edges.get(i).first);
            Node second = nodes.get(edges.get(i).second);
            i++;

            if (first.getInputs().contains(second.getId())) {
                continue;
            }

            totalEdges++;
            first.getInputs().add(second.getId());
            first.getOutputs().add(second.getId());
            second.getInputs().add(first.getId());
            second.getOutputs().add(first.getId());


        }
    }


    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    private static class Pair {
        public int first;
        public int second;
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
}
