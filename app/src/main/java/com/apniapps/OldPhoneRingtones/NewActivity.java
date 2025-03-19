package com.apniapps.OldPhoneRingtones;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class NewActivity extends AppCompatActivity {

    private String ringtoneName;
    private String ringtoneType;
    AdRequest adRequest;
    AdView adView;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);

        // Retrieve the ringtone name and type from the intent
        Intent intent = getIntent();
        ringtoneName = intent.getStringExtra("RINGTONE_NAME");
        ringtoneType = intent.getStringExtra("RINGTONE_TYPE");

        Button contactButton = findViewById(R.id.contactButton);
        Button alarmButton = findViewById(R.id.alarmButton);
        Button smsButton = findViewById(R.id.smsButton);
        adView = findViewById(R.id.adView2);
        contactButton.setOnClickListener(this::setRingtoneForAllCalls);
        alarmButton.setOnClickListener(this::setRingtoneForAlarm);
        smsButton.setOnClickListener(this::setRingtoneForSMS);

        MobileAds.initialize(this);

        adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);
    }

    private void setRingtoneForAllCalls(View view) {
        try {
            Uri ringtoneUri = getRingtoneUri();
            if (ringtoneUri != null) {
                // Set the selected ringtone as the default ringtone for all incoming calls
                RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE, ringtoneUri);
                Toast.makeText(this, "Default call ringtone set successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to set default call ringtone", Toast.LENGTH_SHORT).show();
        }
    }

    private void setRingtoneForAlarm(View view) {
        try {
            Uri ringtoneUri = getRingtoneUri();
            if (ringtoneUri != null) {
                // Set the selected ringtone as the default alarm sound
                RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM, ringtoneUri);
                Toast.makeText(this, "Alarm ringtone set successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to set alarm ringtone", Toast.LENGTH_SHORT).show();
        }
    }

    private void setRingtoneForSMS(View view) {
        try {
            Uri ringtoneUri = getRingtoneUri();
            if (ringtoneUri != null) {
                // Set the selected ringtone as the default SMS notification sound
                RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION, ringtoneUri);
                Toast.makeText(this, "SMS ringtone set successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to set SMS ringtone", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri getRingtoneUri() {
        // Retrieve the resource ID of the selected ringtone
        int resourceId = getResources().getIdentifier(
                ringtoneName.toLowerCase().replace(" ", "_"),
                "raw",
                getPackageName()
        );

        if (resourceId == 0) {
            Toast.makeText(this, "Ringtone not found", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Convert the resource ID to a Uri
        return Uri.parse("android.resource://" + getPackageName() + "/" + resourceId);
    }

}
