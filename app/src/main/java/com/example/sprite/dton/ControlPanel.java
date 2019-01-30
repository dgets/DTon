package com.example.sprite.dton;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
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
    RecyclerView recyclerView;
    ToneDefinitionAdapter adapter;

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
        recyclerView = (RecyclerView) findViewById(R.id.rvwFrequencies);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            myTones = Permanence.getPresetFreqs(getBaseContext());
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), "Issue loading presets: " +
                    ex.toString(), Toast.LENGTH_LONG).show();
        }

        if (myTones == null) {
            myTones = new ArrayList();
        }

        //updateDisplay(myTones);
        adapter = new ToneDefinitionAdapter(this, myTones);
        recyclerView.setAdapter(adapter);
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
            //updateDisplay(myTones);
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
}
