package com.example.media_player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import java.io.File;


public class myService extends Service {


    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String voice = Environment.getExternalStorageDirectory().getPath() + "/Download/SUPER_JUNIOR.mp3";
        Log.d("MAIN",voice);
        File file = new File(voice);
        Log.d("Main", " voice exists : " + file.exists() + ", can read : " + file.canRead());
        MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.parse("file://" + voice));
        if (mediaPlayer==null){
            Toast.makeText(this,"media player is null",Toast.LENGTH_LONG).show();
        }
        else
            mediaPlayer.start();

        return START_STICKY;

       /* if (player == null) {
            player = MediaPlayer.create(this, R.raw.mysong);
            player.setLooping(true);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                }
            });
        }
        player.start();
        return START_STICKY;*/
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();

    }


}
