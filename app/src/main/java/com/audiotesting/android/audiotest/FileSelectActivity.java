package com.audiotesting.android.audiotest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FilePermission;

public class FileSelectActivity extends AppCompatActivity {
    private Context context;
    private Button b1,b2,b3,b4, bConfirm;
    private File f1, f2, f3, f4;
    private Uri u1, u2, u3, u4;
    private AudioPlayerThreadHolder apth = new AudioPlayerThreadHolder();
    private LinearLayout linearFileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);

        final int amountOfFiles;
        context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        amountOfFiles = prefs.getInt("choice",1);
        linearFileLayout = findViewById(R.id.linearFileLayout);
        b1 = new Button(this);
        b2 = new Button(this);
        b3 = new Button(this);
        b4 = new Button(this);
        Button[] buttons = {b1, b2, b3, b4};

        int index = 0;
        for (Button b : buttons) {
            index++;
            b.setText("Select audio file");

            final int finalIndex = index;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performFileSearch(finalIndex);
                }
            });
        }

        for (int i = 0; i < amountOfFiles; i++) {
            linearFileLayout.addView(buttons[i]);
        }

        Button bConfirm = new Button(this);
        bConfirm.setText("Confirm");
        linearFileLayout.addView(bConfirm);

        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (amountOfFiles) {
                    case 1:
                        apth.newAudioPlayerThread(context, u1);
                        break;
                    case 2:
                        apth.newAudioPlayerThread(context, u1);
                        apth.newAudioPlayerThread(context, u2);
                        break;
                    case 3:
                        apth.newAudioPlayerThread(context, u1);
                        apth.newAudioPlayerThread(context, u2);
                        apth.newAudioPlayerThread(context, u3);
                        break;
                    case 4:
                        apth.newAudioPlayerThread(context, u1);
                        apth.newAudioPlayerThread(context, u2);
                        apth.newAudioPlayerThread(context, u3);
                        apth.newAudioPlayerThread(context, u4);
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        switch(requestCode) {
            case 1:
                b1.setText(resultData.getData().getPath());
                u1 = resultData.getData();
                f1 = new File(u1.getPath());
                break;
            case 2:
                b2.setText(resultData.getData().getPath());
                u2 = resultData.getData();
                f2 = new File(u2.getPath());
                break;
            case 3:
                b3.setText(resultData.getData().getPath());
                u3 = resultData.getData();
                f3 = new File(u3.getPath());
                break;
            case 4:
                b4.setText(resultData.getData().getPath());
                u4 = resultData.getData();
                f4 = new File(u4.getPath());
                break;
        }
    }

    public void performFileSearch(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("audio/*");
        startActivityForResult(intent, requestCode);
    }
}
