package com.example.sprite.dton;

import java.util.ArrayList;
import java.util.List;

import static com.example.sprite.dton.ControlPanel.myTones;

/**
 * Our class for each preset tone object, which we're holding a List of in
 * order to store our user inputted data.
 */
public class ToneDefinition {
    private String name;
    private float frequency;
    private boolean playing = false;

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

    public boolean isPlaying() {
        return this.playing;
    }

    public boolean togglePlaying() {
        this.playing = !this.playing;

        return this.playing;
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
     * Method wipes an entry from the internal data structure that we're
     * currently working with, making sure to cleanse it in a way that will
     * leave the List suitable for updateDisplay().
     *
     * @param position List index to toast
     * @return
     */
    public static List<ToneDefinition> wipeEntry(int position) {
        List<ToneDefinition> newTones = new ArrayList<ToneDefinition>();

        myTones.remove(position);
        for (ToneDefinition tone : myTones) {
            if (tone != null) {
                newTones.add(tone);
            }
        }
        myTones = newTones;

        return myTones;
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
