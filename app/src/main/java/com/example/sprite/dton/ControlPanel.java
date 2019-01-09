package com.example.sprite.dton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends AppCompatActivity {

    //control declarations
    private EditText edtFrequency;
    private EditText edtName;

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

        //other

    }

    public void btnAddFreq_onClick(android.view.View view) {
        //let's make sure we've got what we need here, first
        float newFreq = 0;
        String newFreqName;
        boolean err = false;

        try {
            newFreq = Float.parseFloat(edtFrequency.getText().toString());
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Enter an appropriate frequency", Toast.LENGTH_SHORT).show();
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
        } else if (myTones.size() >= Constants.MaxPresets) {
            Toast.makeText(getBaseContext(), "You've reached the maximum number of presets!",
                           Toast.LENGTH_SHORT).show();
        } else if (newFreq == 0) {
            Toast.makeText(getBaseContext(), "Houston, we have a problem...",
                           Toast.LENGTH_SHORT).show();
        }
    }

    public void edtFrequency_onClick(android.view.View view) {
        edtFrequency.setText("");
    }

    public void edtName_onClick(android.view.View view) {
        edtName.setText("");
    }
}
