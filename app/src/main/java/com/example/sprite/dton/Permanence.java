package com.example.sprite.dton;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Permanence {
    /**
     * Contains methods and data related to keeping our data stored somewhere.
     */

    private static final String  PrefsName = "PresetsDef";
    private static final String  FreqEntryName = "freqEntryNameKey";
    private static final String  FreqEntryValue = "freqEntryValueKey";
    private static SharedPreferences myPresetsList;

    public static void savePresetFreqs(Context context, List<ToneDefinition> presetList)
            throws Exception {
        /**
         * Saves preset frequencies list via the SharedPreferences.Editor
         */

        myPresetsList = context.getSharedPreferences(PrefsName, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPresetsList.edit();

        for (int cntr = 1; cntr <= presetList.size(); cntr++) {
            prefsEditor.putString(FreqEntryName + Integer.toString(cntr),
                    presetList.get(cntr - 1).name);
            prefsEditor.putFloat(FreqEntryValue + Integer.toString(cntr),
                    presetList.get(cntr -1).frequency);

            try {
                prefsEditor.commit();   //note that .apply() will do this in the bg instead of
                                        //immediately
            } catch (Exception ex) {
                throw new Exception("Issues committing to SharedPreferences");
            }
        }

        return;
    }

    public static List<ToneDefinition> getPresetFreqs(Context context) throws Exception {
        /**
         * Loads the preset frequencies list (if available) via
         * getSharedPreferences
         */
        myPresetsList = context.getSharedPreferences(PrefsName, Context.MODE_PRIVATE);
        Map<String, ?> allPresets = myPresetsList.getAll();
        List<ToneDefinition> myTonesList = new ArrayList();

        for (int cntr = 1; cntr <= (allPresets.entrySet().size() / 2); cntr++) {
            try {
                myTonesList.add(new ToneDefinition(myPresetsList.getString(
                        FreqEntryName + Integer.toString(cntr), "-"),
                        myPresetsList.getFloat(FreqEntryValue + Integer.toString(cntr), -1)));
            } catch (Exception ex) {
                throw new Exception("Problem retrieving presets: " + ex.toString());
            }
        }

        return myTonesList;
    }
}
