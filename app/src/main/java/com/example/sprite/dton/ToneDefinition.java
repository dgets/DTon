package com.example.sprite.dton;

public class ToneDefinition {
    String name;
    float frequency;

    public ToneDefinition(String name, float freq) {
        this.name = name;
        this.frequency = freq;
    }

    public String toString() {
        return (this.name + ": " + this.frequency);
    }
}
