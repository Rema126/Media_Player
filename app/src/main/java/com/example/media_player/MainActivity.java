package com.example.media_player;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.media_player.App.CHANNEL_ID;

public class MainActivity extends AppCompatActivity {

    final static int MY_PERMISSIONS_REQUEST = 1;
    private NotificationManagerCompat myNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myNotification = NotificationManagerCompat.from(this);

        Button play = findViewById(R.id.playbtn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // USER PERMISSION
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();

                    Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                            0, notificationIntent, 0);
                    Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                            .setContentTitle("Music is running")
                            .setSmallIcon(R.drawable.ic_android)
                            .setContentIntent(pendingIntent)
                            .build();
                    myNotification.notify(1, notification);
                    ContextCompat.startForegroundService(MainActivity.this, new Intent(getBaseContext(), myService.class));
                    startService(new Intent(MainActivity.this, myService.class));

                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(MainActivity.this, "Permission Needed", Toast.LENGTH_LONG).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
                }
            }
        });
    }

    public void stop (View v){
        stopService(new Intent(this,myService.class));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show();


            }else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}


