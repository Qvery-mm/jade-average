package ru.spbu.topology;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FixedRingGraph implements IGraph {
    private final int size;
    private List<Node> nodes;

    public FixedRingGraph(int size) {
        assert (size > 2);
        this.size = size;
        this.nodes = new ArrayList<>(size);
    }

    @Override
    public void create() {
        reset();
        nodes.get(0).getInputs().add(size - 1);
        nodes.get(0).getOutputs().add(size - 1);
        nodes.get(0).getInputs().add(1);
        nodes.get(0).getOutputs().add(1);
        nodes.get(size-1).getInputs().add(0);
        nodes.get(size-1).getOutputs().add(0);
        nodes.get(size-1).getInputs().add(size-2);
        nodes.get(size-1).getOutputs().add(size-2);

        for (int i = 1; i < size-1; i++) {
            nodes.get(i).getOutputs().add(i-1);
            nodes.get(i).getOutputs().add(i+1);
            nodes.get(i).getInputs().add(i-1);
            nodes.get(i).getInputs().add(i+1);
        }
    }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    private void reset() {
        this.nodes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            nodes.add(i, new Node(i));
        }
    }
}
