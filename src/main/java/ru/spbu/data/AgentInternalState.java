package ru.spbu.data;

import java.util.Random;

public class AgentInternalState {
    private float value;

    public AgentInternalState(Random random) {
        this.value = random.nextInt();
    }

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

}
