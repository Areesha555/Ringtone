package com.apniapps.OldPhoneRingtones;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatDelegate;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private RingtoneAdapter adapter;
    private Button callerTonesButton;
    private Button messageTonesButton;
    private Button alarmTonesButton;
    public String currentCategory = "Caller Tones";
    AdRequest adRequest;
    AdView adView;
    private InterstitialAd interstitialAd;





    private final ArrayList<String> callerTones = new ArrayList<>();
    private final ArrayList<String> messageTones = new ArrayList<>();
    private final ArrayList<String> alarmTones = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize ListView and add ringtone data
        listView = findViewById(R.id.listView);
        callerTonesButton = findViewById(R.id.callerTonesButton);
        messageTonesButton = findViewById(R.id.messageTonesButton);
        alarmTonesButton = findViewById(R.id.alarmButton);
        adView = findViewById(R.id.adView);
        loadAudioFiles(); // Load audio files
        loadRingtones(callerTones);

        getSupportActionBar().setTitle("Old Phone: Ringtones");
        callerTonesButton.setOnClickListener(view -> {
            if (adapter != null) {
                adapter.pauseMediaPlayer(); // Stop the previous tone
            }
            currentCategory = "Caller Tones";
            loadRingtones(callerTones);
        });

        messageTonesButton.setOnClickListener(view -> {
            if (adapter != null) {
                adapter.pauseMediaPlayer(); // Stop the previous tone
            }
            currentCategory = "Message Tones";
            loadRingtones(messageTones);

        });

        alarmTonesButton.setOnClickListener(view -> {
            if (adapter != null) {
                adapter.pauseMediaPlayer(); // Stop the previous tone
            }
            currentCategory = "Alarm Tones";
            loadRingtones(alarmTones);

        });
        MobileAds.initialize(this);

        adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);



    }



    @Override
    protected void onResume() {
        super.onResume();
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd ad) {
                        interstitialAd = ad;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        interstitialAd = null;
                    }
                });

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (adapter != null) {
          adapter.pauseMediaPlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.releaseMediaPlayer(); // Ensure you have a method in your adapter to release the media player
        }
    }

    private void loadAudioFiles() {
        // Add your audio file paths here (assumes you have .mp3 files in res/raw)
       // caller tone
        callerTones.add("old_ringtone");
        callerTones.add("marimba");
        callerTones.add("vintage_telephone");
        callerTones.add("discord_call");
        callerTones.add("horizon");
        callerTones.add("classical");
        callerTones.add("jazz_play");
        callerTones.add("hip_hop");
        callerTones.add("ocean_waves");
        callerTones.add("guitar_melow");
        callerTones.add("rythm_blues");
        callerTones.add("jazz_beat");
        callerTones.add("most_unique");
        callerTones.add("ghazali");
        callerTones.add("i_can_be");
        callerTones.add("tipsy");
        callerTones.add("lamine");
        callerTones.add("special_tone");
        callerTones.add("jungle");
        callerTones.add("bubbly_bubbles");
        callerTones.add("universfield");
        callerTones.add("meri_maa");
        callerTones.add("ertugrul_ghazi");
        callerTones.add("cradles_urban");
        callerTones.add("maa_ka_phone");
        callerTones.add("martin_animals");
        callerTones.add("i_love_you");
        callerTones.add("iphone");
        callerTones.add("miss_you");
        callerTones.add("blackberry");
        callerTones.add("romance");
        callerTones.add("gitar");
        callerTones.add("mystic_call");
        callerTones.add("samsung");

        // sms tones
        messageTones.add("runner");
        messageTones.add("beep");
        messageTones.add("twinkle");
        messageTones.add("bubbles");
        messageTones.add("notes");
        messageTones.add("snoozy");
        messageTones.add("mario");
        messageTones.add("bone_riff");
        messageTones.add("kill_bill");
        messageTones.add("popup");
        messageTones.add("phone");
        messageTones.add("under_sea");
        messageTones.add("pacman");
        messageTones.add("scary_lala");
        messageTones.add("harry_potter");
        messageTones.add("purge_siren");
        messageTones.add("usher");
        messageTones.add("funkytown");
        messageTones.add("jetsons");
        messageTones.add("sugarsugar");
        // alarm tones
        alarmTones.add("flowers");
        alarmTones.add("retro_theme");
        alarmTones.add("ringing_bells");
        alarmTones.add("whistle");
        alarmTones.add("light_notes");
        alarmTones.add("bells");
        alarmTones.add("bip_bip");
        alarmTones.add("disco_theme");
        alarmTones.add("horde");
        alarmTones.add("hyrule");
        alarmTones.add("ogclassic");
        alarmTones.add("amoung_us");
        alarmTones.add("ray_normal");
        alarmTones.add("alarm_2");
        alarmTones.add("baby_tone");
        alarmTones.add("super_bells");
        alarmTones.add("beautiful_bells");
        alarmTones.add("the_rose");
        alarmTones.add("super_piano");
        alarmTones.add("leaf_rag");
    }

    private void loadRingtones(ArrayList<String> tones) {
        adapter = new RingtoneAdapter(this, tones, currentCategory, interstitialAd);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if(itemID==R.id.revertTune){
            revertToDefaultRingtones();
            return true;
        }
        else if (itemID==R.id.privacy)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1oyoC-IxN8kaxCYrWibH4jiKwImP1coAD2eqKTfSpQfE"));
            startActivity(intent);
            return true;
        } else if (itemID==R.id.share) {
            shareApp();
            return true;
        }
        else if (itemID== R.id.rateUs){
            rateApp();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }




    }
    private void revertToDefaultRingtones() {
        try {
            // Reset the default ringtone for incoming calls
            RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE,
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

            // Reset the default ringtone for alarms
            RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM,
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));

            // Reset the default ringtone for notifications
            RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION,
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            // Optionally, clear any saved custom settings in SharedPreferences
            SharedPreferences preferences = getSharedPreferences("RingtonePreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear(); // Clears all saved custom ringtones
            editor.apply();

            Toast.makeText(this, "Reverted to default ringtones", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to revert to default ringtones", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareMessage = "Check out this amazing ringtone app: [Your App Link]";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Share app via"));
    }
    private void rateApp() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check if the request code matches
        if (requestCode == RingtoneAdapter.WRITE_SETTINGS_REQUEST_CODE) {
            if (Settings.System.canWrite(this)) {
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                // Optionally, you could navigate to the new activity here or perform any action needed.
            } else {
                Toast.makeText(this, "Permission not granted.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}