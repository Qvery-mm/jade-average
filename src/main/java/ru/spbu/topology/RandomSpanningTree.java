package ru.spbu.topology;

import java.util.*;

public class RandomSpanningTree implements IGraph {
    private final Random random;
    private final int size;
    private List<Node> nodes;

    public RandomSpanningTree(int size, int seed) {
        assert (size > 1);
        this.random = new Random(seed);
        this.size = size;
        this.nodes = new ArrayList<>(size);
    }

    @Override
    public void create() {
        reset();
        Collections.shuffle(nodes, random);

        for (int i = 1; i < size; i++) {
            int idx = random.nextInt(i);
            Node parent = nodes.get(idx);
            Node child = nodes.get(i);
            parent.getInputs().add(child.getId());
            parent.getOutputs().add(child.getId());
            child.getInputs().add(parent.getId());
            child.getOutputs().add(parent.getId());
        }
        nodes.sort((a, b) -> -Integer.compare(b.getId(), a.getId()));
    }

    private void reset() {
        this.nodes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            nodes.add(i, new Node(i));
        }
    }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }
}
