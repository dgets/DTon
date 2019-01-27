package com.example.sprite.dton;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends AppCompatActivity {
    /**
     * Main display and entry point.
     */

    //control declarations
    private EditText edtFrequency;
    private EditText edtName;
    private ScrollView scrPresetList;
    private TableLayout tloToneList;
    private TableRow trwToneListRow;

    //other schitt
    private List<ToneDefinition> myTones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Takes care of the initialization of several bits, also attempts to
         * load preset values from the database and updates the display so as
         * to make the list visible to the user.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        //controls
        edtFrequency = (EditText) findViewById(R.id.edtFrequency);
        edtName = (EditText) findViewById(R.id.edtName);
        scrPresetList = (ScrollView) findViewById(R.id.scrFreqSet);
        tloToneList = (TableLayout) findViewById(R.id.tloToneListing);
        trwToneListRow = (TableRow) findViewById(R.id.trwFreqEntry);

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

    public void btnAddFreq_onClick(android.view.View view) {
        /**
         * Adds the information (if validated properly) to the internal list
         * of preset frequencies, saves them to the SharePreferences, and adds
         * them to the preset list display.
         */

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

        if (!err && (myTones.size() < Constants.MaxPresets) && (newFreq != 0)) {
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

            if (Constants.Debugging) {
                Toast.makeText(context, "myTones contains: " + myTones.toString(),
                        Toast.LENGTH_LONG).show();
            }

        } else if (myTones.size() >= Constants.MaxPresets) {
            Toast.makeText(context, "You've reached the maximum number of presets!",
                           Toast.LENGTH_SHORT).show();
        } else if (newFreq == 0) {
            //looks like this is happening primarily when a non-float value is being entered
            Toast.makeText(context, "Houston, we have a problem...",
                           Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDisplay(List<ToneDefinition> toneList) {
        /**
         * Handles addition of the presets list elements to the TextView used
         * for their display.
         *
         * TODO: Swap TextView for RecycleWhatever that will provide the selectable functionality that we need
         */

        TextView tvwRowContent = (TextView) trwToneListRow.findViewById(R.id.tvwFreqListEntry);

        if (toneList.size() == 0) {
            tvwRowContent.setText("No frequency entries exist");

            return;
        }

        tvwRowContent.setText("\n");
        for (ToneDefinition tListEntry : toneList) {
            //dynamic addition of entries
            if (Constants.Debugging) {
                Toast.makeText(getBaseContext(), "Reached dynamic addition",
                        Toast.LENGTH_SHORT).show();
            }

            //tvwRowContent.setId(cntr);
            tvwRowContent.append(tListEntry.name + ": " + tListEntry.frequency + "Hz\n");
        }
    }
}
