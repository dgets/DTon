package com.example.sprite.dton;

public class ToneDefinition {
    /**
     * Our class for each preset tone object, which we're holding a List of in
     * order to store our user inputted data.
     */
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
