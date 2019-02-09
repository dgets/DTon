package com.example.sprite.dton;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Contains methods and data related to keeping our data stored somewhere.
 */
public class Permanence {
    private static final String  PrefsName = "PresetsDef";
    private static final String  FreqEntryName = "freqEntryNameKey";
    private static final String  FreqEntryValue = "freqEntryValueKey";
    private static SharedPreferences myPresetsList;

    /**
     * Saves preset frequencies list via the SharedPreferences.Editor
     *
     * @param context current context
     * @param presetList internal data structure for preset frequencies
     */
    public static void savePresetFreqs(Context context, List<ToneDefinition> presetList)
            throws Exception {
        myPresetsList = context.getSharedPreferences(PrefsName, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPresetsList.edit();

        for (int cntr = 0; cntr < presetList.size(); cntr++) {
            Log.d("savePresetFreqs", presetList.get(cntr).toString());

            prefsEditor.putString(FreqEntryName + Integer.toString(cntr),
                    presetList.get(cntr).getName());
            prefsEditor.putFloat(FreqEntryValue + Integer.toString(cntr),
                    presetList.get(cntr).getFrequency());

            try {
                prefsEditor.commit();   //note that .apply() will do this in the bg instead of
                                        //immediately
            } catch (Exception ex) {
                throw new Exception("Issues committing to SharedPreferences");
            }
        }

        return;
    }

    /**
     * Loads the preset frequencies list (if available) via
     * getSharedPreferences
     *
     * @param context current context
     * @return List internal data structure representing preset frequencies
     */
    public static List<ToneDefinition> getPresetFreqs(Context context) throws Exception {
        myPresetsList = context.getSharedPreferences(PrefsName, Context.MODE_PRIVATE);
        Map<String, ?> allPresets = myPresetsList.getAll();
        List<ToneDefinition> myTonesList = new ArrayList();

        Log.d("getPresetFreqs", "allPresets contains: " + allPresets.toString());
        Log.d("getPresetFreqs", "allPresets size: " +
                Integer.toString(allPresets.entrySet().size()));
        
        for (int cntr = 0; cntr < (allPresets.entrySet().size() / 2); cntr++) {
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

    public static void removePresetFreq(Context context, int ndx) {
        //SharedPreferences prefs = context.getSharedPreferences(PrefsName, Context.MODE_PRIVATE);
        SharedPreferences prefs = myPresetsList;
        boolean success = true;

        Log.d("removePresetFreq", "Trying to remove entry #" + Integer.toString(ndx));
        if (!prefs.edit().remove(FreqEntryName + Integer.toString(ndx)).commit()) {
            success = false;
        }
        if (!prefs.edit().remove(FreqEntryValue + Integer.toString(ndx)).commit()) {
            success = false;
        }

        if (!success) {
            Log.d("removePresetFreq", "Could not successfully write changes to prefs!");
        } else {
            Log.d("removePresetFreq", "Successfully wrote changes to prefs.");
        }
    }
}
