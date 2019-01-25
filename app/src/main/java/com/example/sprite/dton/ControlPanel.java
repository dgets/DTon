package com.example.sprite.dton;

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

    //control declarations
    private EditText edtFrequency;
    private EditText edtName;
    private ScrollView scrPresetList;
    private TableLayout tloToneList;
    private TableRow trwToneListRow;

    //other schitt
    //private ToneDefinition[] myTones = new ToneDefinition[Constants.MaxPresets];
    List<ToneDefinition> myTones = new ArrayList<ToneDefinition>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        //controls
        edtFrequency = (EditText) findViewById(R.id.edtFrequency);
        edtName = (EditText) findViewById(R.id.edtName);
        scrPresetList = (ScrollView) findViewById(R.id.scrFreqSet);
        tloToneList = (TableLayout) findViewById(R.id.tloToneListing);
        trwToneListRow = (TableRow) findViewById(R.id.trwFreqEntry);

        updateDisplay(myTones);
    }

    public void btnAddFreq_onClick(android.view.View view) {
        //let's make sure we've got what we need here, first
        float newFreq = 0;
        String newFreqName;
        boolean err = false;

        try {
            newFreq = Float.parseFloat(edtFrequency.getText().toString());
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Enter an appropriate frequency",
                           Toast.LENGTH_SHORT).show();
            edtFrequency.setText(R.string.freq_value_textbox);
            err = true;
        }

        newFreqName = edtName.getText().toString();
        if (newFreqName.equals(R.string.freq_name_box) || newFreqName.equals("")) {
            Toast.makeText(getBaseContext(), "Enter a name/desc for your frequency choice",
                           Toast.LENGTH_SHORT).show();
            edtName.setText(R.string.freq_name_box);
            err = true;
        }

        if (!err && (myTones.size() < Constants.MaxPresets) && (newFreq != 0)) {
            myTones.add(new ToneDefinition(newFreqName, newFreq));
            edtFrequency.setText(R.string.freq_value_textbox);
            edtName.setText(R.string.freq_name_box);
            updateDisplay(myTones);

            if (Constants.Debugging) {
                Toast.makeText(getBaseContext(), "myTones contains: " + myTones.toString(),
                        Toast.LENGTH_LONG).show();
            }

        } else if (myTones.size() >= Constants.MaxPresets) {
            Toast.makeText(getBaseContext(), "You've reached the maximum number of presets!",
                           Toast.LENGTH_SHORT).show();
        } else if (newFreq == 0) {
            Toast.makeText(getBaseContext(), "Houston, we have a problem...",
                           Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDisplay(List<ToneDefinition> toneList) {
        if (toneList.size() == 0) {
            TextView tvwRowContent = (TextView) trwToneListRow.findViewById(R.id.tvwFreqListEntry);

            tvwRowContent.setText("No frequency entries exist");

            return;
        }

        int cntr = 0;
        for (ToneDefinition tListEntry : toneList) {
            //dynamic addition of entries
            TextView tvwRowContent = new TextView(getBaseContext());

            if (Constants.Debugging) {
                Toast.makeText(getBaseContext(), "Reached dynamic addition",
                        Toast.LENGTH_SHORT).show();
            }

            tvwRowContent.setId(cntr);
            tvwRowContent.setText(tListEntry.name + ": " + tListEntry.frequency + "Hz");

            try {
                trwToneListRow.addView(tvwRowContent);
            } catch (Exception ex) {
                Toast.makeText(getBaseContext(),
                        "Exception in trwToneListRow.addView(tvwRowContent) " + ex.toString(),
                        Toast.LENGTH_LONG).show();
            }
            try {
                scrPresetList.addView(trwToneListRow);
            } catch (Exception ex) {
                Toast.makeText(getBaseContext(),
                        "Exception in scrPresetList.addView(trwToneListRow) " + ex.toString(),
                        Toast.LENGTH_LONG).show();
            }
            try {
                tloToneList.addView(scrPresetList);
            } catch (Exception ex) {
                Toast.makeText(getBaseContext(),
                        "Exception in tloToneList.addView(scrPresetList) " + ex.toString(),
                        Toast.LENGTH_LONG).show();
            }

        }
    }

    public void edtFrequency_onClick(android.view.View view) {
        edtFrequency.setText("");
    }

    public void edtName_onClick(android.view.View view) {
        edtName.setText("");
    }
}
