package com.audiotesting.android.audiotest;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Process;

import java.io.File;

public class AudioPlayerThreadHolder {
    private Thread audioPlayerThread = null;
    public AudioPlayerThreadHolder() {
    }

    public void newAudioPlayerThread(final Context context, final Uri uri) {
        audioPlayerThread = new Thread(new Runnable() {
            MediaPlayer player = new MediaPlayer();

            @Override
            public void run() {
                try {
                    if (player == null)
                        player = new MediaPlayer();
                    player.setDataSource(context, uri);
                    player.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    player.start();
                }
            }
        });
        audioPlayerThread.start();
    }
}
