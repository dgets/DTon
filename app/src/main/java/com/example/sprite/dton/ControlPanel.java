package com.example.sprite.dton;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Main display and entry point.
 */
public class ControlPanel extends AppCompatActivity {
    //control declarations
    private EditText edtFrequency;
    private EditText edtName;
    private LinearLayout lloPresets;

    //other schitt
    private List<ToneDefinition> myTones;

    /**
     * Takes care of the initialization of several bits, also attempts to
     * load preset values from the database and updates the display so as
     * to make the list visible to the user.
     *
     * @param savedInstanceState typical onCreate() crapola
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        //controls
        edtFrequency = (EditText) findViewById(R.id.edtFrequency);
        edtName = (EditText) findViewById(R.id.edtName);
        lloPresets = (LinearLayout) findViewById(R.id.lloPresets);

        try {
            myTones = Permanence.getPresetFreqs(getBaseContext());
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), "Issue loading presets: " +
                    ex.toString(), Toast.LENGTH_LONG).show();
        }

        if (myTones == null) {
            myTones = new ArrayList();
        }

        updateDisplay(myTones);
    }

    /**
     * Adds the information (if validated properly) to the internal list
     * of preset frequencies, saves them to the SharePreferences, and adds
     * them to the preset list display.
     *
     * @param view current view
     */
    public void btnAddFreq_onClick(android.view.View view) {
        //let's make sure we've got what we need here, first
        float newFreq = 0;
        String newFreqName;
        boolean err = false;
        Context context = getBaseContext();

        try {
            newFreq = Float.parseFloat(edtFrequency.getText().toString());
        } catch (Exception e) {
            Toast.makeText(context, "Enter an appropriate frequency",
                           Toast.LENGTH_SHORT).show();
            edtFrequency.setText("");
            err = true;
        }

        newFreqName = edtName.getText().toString();
        if (newFreqName.equals(R.string.freq_name_box) || newFreqName.equals("")) {
            Toast.makeText(context, "Enter a name/desc for your frequency choice",
                           Toast.LENGTH_SHORT).show();
            edtName.setText("");
            err = true;
        }

        if (!err && (myTones.size() < GlobalMisc.MaxPresets) && (newFreq != 0)) {
            myTones.add(new ToneDefinition(newFreqName, newFreq));
            edtFrequency.setText("");
            edtName.setText("");
            updateDisplay(myTones);
            try {
                Permanence.savePresetFreqs(context, myTones);
            } catch (Exception ex) {
                Toast.makeText(context, "Error saving shared preferences: " +
                        ex.toString(), Toast.LENGTH_LONG).show();
            }

            if (GlobalMisc.Debugging) {
                Toast.makeText(context, "myTones contains: " + myTones.toString(),
                        Toast.LENGTH_LONG).show();
            }

        } else if (myTones.size() >= GlobalMisc.MaxPresets) {
            Toast.makeText(context, "You've reached the maximum number of presets!",
                           Toast.LENGTH_SHORT).show();
        } else if (newFreq == 0) {
            //looks like this is happening primarily when a non-float value is being entered
            Toast.makeText(context, "Houston, we have a problem... (non-float value?)",
                           Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDisplay(List<ToneDefinition> myTones) {
        Context context = getBaseContext();
        LinearLayout.LayoutParams lparams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView[] presetsTextView = new TextView[GlobalMisc.MaxPresets];
        final List<ToneDefinition> toneList = myTones;

        //wipe anything that may've been in the layout prior to coming here
        lloPresets.removeAllViewsInLayout();

        //add our presets to the linearlayout in textviews
        for (int cntr = 0; cntr < myTones.size() && cntr < GlobalMisc.MaxPresets; cntr++) {
            presetsTextView[cntr] = new TextView(context);
            presetsTextView[cntr].setLayoutParams(lparams);
            presetsTextView[cntr].setText(myTones.get(cntr).getName() + ": " +
                    myTones.get(cntr).getFrequency() + "Hz");

            final int ouah = cntr;

            presetsTextView[cntr].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean alreadyPlaying = false;
                    int playingIndex = -1;

                    for (int cntr = 0; cntr < toneList.size(); cntr++) {
                        if (toneList.get(cntr).isPlaying()) {
                            alreadyPlaying = true;
                            playingIndex = cntr;
                            break;
                        }
                    }

                    if (alreadyPlaying && playingIndex == ouah) {
                        toneList.get(ouah).togglePlaying();
                        PlayTone.stop();
                    } else if (alreadyPlaying) {
                        Toast.makeText(getBaseContext(), "Already playing a tone, stop '" +
                                        toneList.get(playingIndex).getName() + "' first!",
                                        Toast.LENGTH_LONG).show();
                    } else {
                        toneList.get(ouah).togglePlaying();
                        PlayTone.play(toneList.get(ouah).getFrequency());
                        Toast.makeText(getBaseContext(), "Playing '" +
                                toneList.get(ouah).getName() + "'", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            lloPresets.addView(presetsTextView[cntr]);
        }
    }
}
