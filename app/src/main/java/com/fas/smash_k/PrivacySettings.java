package com.fas.smash_k;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.fas.smash_k.R;


public class PrivacySettings extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy);

        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(),"Swithc is checked ON",Toast.LENGTH_LONG).show();
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                    Toast.makeText(getApplicationContext(),"Swithc is checked OFF",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
