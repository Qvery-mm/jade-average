package ru.spbu.data;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class AgentInternalState {
    private float value;
    private Queue<Float> queue = new LinkedList<>();

    public AgentInternalState(Random random) {
        this.value = random.nextInt() % 100;
    }

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    public Queue<Float> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Float> queue) {
        this.queue = queue;
    }

}
