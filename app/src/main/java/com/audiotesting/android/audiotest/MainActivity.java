package com.audiotesting.android.audiotest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioButton radio1,radio2,radio3,radio4;
    RadioGroup radioGroup;
    Button confirmButton;
    SharedPreferences.Editor prefEditor;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefEditor = prefs.edit();
        
        radioGroup = findViewById(R.id.fileChoiceRadioGroup);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radio3 = findViewById(R.id.radio3);
        radio4 = findViewById(R.id.radio4);
        confirmButton = findViewById(R.id.confirmButton);

        if (prefs.contains("choice")) {
            switch(prefs.getInt("choice",1)) {
                case 1:
                    radio1.setChecked(true);
                    break;
                case 2:
                    radio2.setChecked(true);
                    break;
                case 3:
                    radio3.setChecked(true);
                    break;
                case 4:
                    radio4.setChecked(true);
                    break;
            }
            
        }
        
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case (R.id.radio1):
                        prefEditor.putInt("choice",1);
                        break;

                    case (R.id.radio2):
                        prefEditor.putInt("choice",2);
                        break;

                    case (R.id.radio3):
                        prefEditor.putInt("choice",3);
                        break;

                    case (R.id.radio4):
                        prefEditor.putInt("choice",4);
                        break;
                }
                prefEditor.apply();
            }
        });

        final Intent newFileActivityIntent = new Intent(this, FileSelectActivity.class);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!prefs.contains("choice")) {
                    Toast.makeText(context, "You haven't selected an amount of files!", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(newFileActivityIntent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
