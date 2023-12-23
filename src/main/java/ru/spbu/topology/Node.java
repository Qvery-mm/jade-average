package ru.spbu.topology;

import java.util.HashSet;
import java.util.Set;

public class Node {
    private final int id;
    private final Set<Integer> outputs = new HashSet<>();
    private final Set<Integer> inputs = new HashSet<>();

    public Node(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Set<Integer> getOutputs() {
        return outputs;
    }

    public Set<Integer> getInputs() {
        return inputs;
    }

    @Override
    public String toString() {
        return "Node(" + id + ") {\n" +
                "  {inputs: [" + inputs + "]}\n" +
                "  {outputs: [" + outputs + "]}\n\n"
                + "}";
    }
}
