package com.example.sprite.dton;

/**
 * Our class for each preset tone object, which we're holding a List of in
 * order to store our user inputted data.
 */
public class ToneDefinition {
    String name;
    float frequency;

    public void setName(String name) {
        this.name = name;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public float getFrequency() {
        return frequency;
    }

    /**
     * ToneDefinition constructor method
     *
     * @param name
     * @param freq
     */
    public ToneDefinition(String name, float freq) {
        this.name = name;
        this.frequency = freq;
    }

    /**
     * That normal thing that a toString() method does
     *
     * @return
     */
    public String toString() {
        return (this.name + ": " + this.frequency);
    }
}
