package com.example.sprite.dton;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.List;

public class Permanence {
    public static final String  PrefsName = "PresetsDef";
    //public static final String  FreqEntry = "freqEntryKey";
    public static final String  FreqEntryName = "freqEntryNameKey";
    public static final String  FreqEntryValue = "freqEntryValueKey";

    SharedPreferences myPresetsList;

    public void savePresetFreqs(Context context, List<ToneDefinition> presetList) throws Exception {
        myPresetsList = context.getSharedPreferences(PrefsName, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPresetsList.edit();

        for (int cntr = 1; cntr <= presetList.size(); cntr++) {
            prefsEditor.putString(FreqEntryName + Integer.toString(cntr),
                    presetList.get(cntr - 1).name);
            prefsEditor.putString(FreqEntryValue + Integer.toString(cntr),
                    Float.toString(presetList.get(cntr -1).frequency));

            try {
                prefsEditor.commit();
            } catch (Exception ex) {
                throw new Exception("Issues committing to SharedPreferences");
            }
        }

        return;
    }
}
