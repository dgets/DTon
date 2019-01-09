package com.example.sprite.dton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class ControlPanel extends AppCompatActivity {

    //control declarations
    private EditText edtFrequency;
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        //controls
        edtFrequency = (EditText) findViewById(R.id.edtFrequency);
        edtName = (EditText) findViewById(R.id.edtName);

    }

    public void btnAddFreq_onClick(android.view.View view) {
        //let's make sure we've got what we need here, first
        float newFreq;
        String newFreqName;

        try {
            newFreq = Float.parseFloat(edtFrequency.getText().toString());
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Enter an appropriate frequency", Toast.LENGTH_SHORT).show();
            edtFrequency.setText(R.string.freq_value_textbox);
        }

        newFreqName = edtName.getText().toString();
        if (newFreqName.equals(R.string.freq_name_box) || newFreqName.equals("")) {
            Toast.makeText(getBaseContext(), "Enter a name/desc for your frequency choice",
                           Toast.LENGTH_SHORT).show();
            edtName.setText(R.string.freq_name_box);
        }
    }

    public void edtFrequency_onClick(android.view.View view) {
        edtFrequency.setText("");
    }

    public void edtName_onClick(android.view.View view) {
        edtName.setText("");
    }
}
