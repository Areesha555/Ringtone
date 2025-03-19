package com.apniapps.OldPhoneRingtones;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

public class RingtoneAdapter extends ArrayAdapter<String>  {

    private final Context context;
    private final ArrayList<String> ringtones;
    private MediaPlayer mediaPlayer;
    private final String currentCategory;
    private int currentlyPlayingPosition = -1; // Track the currently playing position
    public ImageButton playButton;
    public static final int WRITE_SETTINGS_REQUEST_CODE = 200;
    private InterstitialAd interstitialAd;
    private int ringtonePlayCount = 0;
    private final int AD_PLAY_THRESHOLD = 6;



    public RingtoneAdapter(Context context, ArrayList<String> ringtones, String currentCategory, InterstitialAd interstitialAd) {
        super(context, R.layout.ringtone_item, ringtones);
        this.context = context;
        this.ringtones = ringtones;
        this.currentCategory = currentCategory;
        this.interstitialAd = interstitialAd;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout if it's not already inflated
        loadNewAd();
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.ringtone_item, parent, false);
        }

        // Get the ringtone name
        String ringtoneNameText = ringtones.get(position);

        // Find the TextView and Buttons in the inflated layout
        TextView ringtoneName = convertView.findViewById(R.id.ringtoneName);
        playButton = convertView.findViewById(R.id.playButton);
        ImageButton navigateButton = convertView.findViewById(R.id.navigateButton);


        ringtoneName.setText(ringtoneNameText.toUpperCase());  // Set the name to the TextView
        setBackgroundBasedOnCategory(position, convertView);
        // Reset play icon for all items
        playButton.setImageResource(R.drawable.baseline_play_arrow_24);

        // If this item is currently playing, change the icon to pause
        if (position == currentlyPlayingPosition) {
            playButton.setImageResource(R.drawable.baseline_pause_24);
        }


        // Set an OnClickListener for the Play button
        playButton.setOnClickListener(v -> {
            ringtonePlayCount++;
            if (ringtonePlayCount >= AD_PLAY_THRESHOLD && interstitialAd != null) {
                interstitialAd.show((Activity) context);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Proceed to play the ringtone after the ad is dismissed
                        playRingtone(ringtoneNameText, position);
                        ringtonePlayCount = 0;
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // If the ad fails to show, play the ringtone without showing the ad
                        playRingtone(ringtoneNameText, position);
                    }
                });
            } else {
                // If the ad isn't loaded, just play the ringtone
                playRingtone(ringtoneNameText, position);
            }



        });

        // Set up the navigation button click to open a new activity
        navigateButton.setOnClickListener(v -> {
            if (!Settings.System.canWrite(context)) {
                showPermissionDialog();
            } else {
                navigateToNewActivity(ringtoneNameText);
            }
        });


        return convertView;
    }

    private void playRingtone(String ringtoneNameText, int position) {
        if (mediaPlayer != null && currentlyPlayingPosition == position) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            currentlyPlayingPosition = -1;
        } else {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            int resourceId = context.getResources().getIdentifier(
                    ringtoneNameText.toLowerCase().replace(" ", "_"),
                    "raw",
                    context.getPackageName()
            );
            mediaPlayer = MediaPlayer.create(context, resourceId);
            mediaPlayer.start();
            currentlyPlayingPosition = position;

            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release();
                mediaPlayer = null;
                currentlyPlayingPosition = -1;
                notifyDataSetChanged();
            });
        }

        notifyDataSetChanged();
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null; // Clear the reference
        }
    }
    private void loadNewAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, "ca-app-pub-3940256099942544/1033173712", adRequest,
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

    private int getCallerToneBackground(int position) {
        // Return different background resources for Caller Tones based on position
        switch (position) {
            case 0: return R.drawable.bg_image_1; // Replace with your drawable resources
            case 1: return R.drawable.bg_image_2;
            case 2: return R.drawable.bg_image_3;
            case 3: return R.drawable.bg_image_4;
            case 4: return R.drawable.bg_image_5;
            case 5: return R.drawable.bg_image_6;
            case 6: return R.drawable.bg_image_7;
            case 7: return R.drawable.bg_image_8;
            case 8: return R.drawable.bg_image_9; // Replace with your drawable resources
            case 9: return R.drawable.bg_image_10;
            case 10: return R.drawable.bg_image_11;
            case 11: return R.drawable.bg_image_12;
            case 12: return R.drawable.bg_image_13;
            case 13: return R.drawable.bg_image_14;
            case 14: return R.drawable.bg_image_15;
            case 15: return R.drawable.bg_image_16;
            case 16: return R.drawable.bg_image_17;
            case 17: return R.drawable.bg_image_18;
            case 18: return R.drawable.bg_image_19;
            case 19: return R.drawable.bg_image_20;
            case 20: return R.drawable.bg_image_21;
            case 21: return R.drawable.bg_image_22;
            case 22: return R.drawable.bg_image_23;
            case 23: return R.drawable.bg_image_24;
            case 24: return R.drawable.bg_image_25;
            case 25: return R.drawable.bg_image_26;
            case 26: return R.drawable.bg_image_27;
            case 27: return R.drawable.bg_image_28;
            case 28: return R.drawable.bg_image_29;
            case 29: return R.drawable.bg_image_30;
            case 30: return R.drawable.bg_image_8;
            case 31: return R.drawable.bg_image_9; // Replace with your drawable resources
            case 32: return R.drawable.bg_image_10;
            case 33: return R.drawable.bg_image_11;
            // Continue for all your caller tones
            default: return R.drawable.phone2; // A default background
        }
    }

    private int getMessageToneBackground(int position) {
        // Return different background resources for Message Tones based on position
        switch (position) {
            case 0: return R.drawable.bg_image_12; // Replace with your drawable resources
            case 1: return R.drawable.bg_image_13;
            case 2: return R.drawable.bg_image_14;
            case 3: return R.drawable.bg_image_10;
            case 4: return R.drawable.bg_image_11;
            case 5: return R.drawable.bg_image_12;
            case 6: return R.drawable.bg_image_10;
            case 7: return R.drawable.bg_image_20;
            case 8: return R.drawable.bg_image_3;
            case 9: return R.drawable.bg_image_30;
            case 10: return R.drawable.bg_image_21;
            case 11: return R.drawable.bg_image_15;
            case 12: return R.drawable.bg_image_1; // Replace with your drawable resources
            case 13: return R.drawable.bg_image_2;
            case 14: return R.drawable.bg_image_3;
            case 15: return R.drawable.bg_image_4;
            case 16: return R.drawable.bg_image_5;
            case 17: return R.drawable.bg_image_6;
            case 18: return R.drawable.bg_image_7;
            case 19: return R.drawable.bg_image_8;
            // Continue for all your message tones
            default: return R.drawable.phone2; // A default background
        }
    }

    private int getAlarmToneBackground(int position) {
        // Return different background resources for Alarm Tones based on position
        switch (position) {
            case 0: return R.drawable.bg_image_30; // Replace with your drawable resources
            case 1: return R.drawable.bg_image_29;
            case 2: return R.drawable.bg_image_11;
            case 3: return R.drawable.bg_image_14;
            case 4: return R.drawable.bg_image_12;
            case 5: return R.drawable.bg_image_8;
            case 6: return R.drawable.bg_image_6;
            case 7: return R.drawable.bg_image_4;
            case 8: return R.drawable.bg_image_2; // Replace with your drawable resources
            case 9: return R.drawable.bg_image_1;
            case 10: return R.drawable.bg_image_3;
            case 11: return R.drawable.bg_image_5;
            case 12: return R.drawable.bg_image_7;
            case 13: return R.drawable.bg_image_17;
            case 14: return R.drawable.bg_image_13;
            case 15: return R.drawable.bg_image_9;
            case 16: return R.drawable.bg_image_10;
            case 17: return R.drawable.bg_image_15;
            case 18: return R.drawable.bg_image_4;
            case 19: return R.drawable.bg_image_1;
            // Continue for all your alarm tones
            default: return R.drawable.phone2; // A default background
        }
    }

    private void setBackgroundBasedOnCategory(int position, View convertView) {
        // Logic to set background based on category
        RelativeLayout layout = (RelativeLayout) convertView; // Cast to RelativeLayout if needed

        if (currentCategory.equals("Caller Tones")) {
            layout.setBackgroundResource(getCallerToneBackground(position));
        } else if (currentCategory.equals("Message Tones")) {
            layout.setBackgroundResource(getMessageToneBackground(position));
        } else if (currentCategory.equals("Alarm Tones")) {
            layout.setBackgroundResource(getAlarmToneBackground(position));
        }
    }
    private void showPermissionDialog() {
        new AlertDialog.Builder(context)
                .setTitle("Permission Required")
                .setMessage("To set ringtones, this app needs permission to modify system settings.")
                .setPositiveButton("Grant Permission", (dialog, which) -> {
                    requestWriteSettingsPermission();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    private void requestWriteSettingsPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        ((MainActivity) context).startActivityForResult(intent, WRITE_SETTINGS_REQUEST_CODE);
    }

    private void navigateToNewActivity(String ringtoneNameText) {
        Intent intent = new Intent(context, NewActivity.class);
        intent.putExtra("RINGTONE_NAME", ringtoneNameText);
        context.startActivity(intent);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public void pauseMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop(); // stop the media player
            currentlyPlayingPosition = -1;
            playButton.setImageResource(R.drawable.baseline_pause_24);
            notifyDataSetChanged();
            mediaPlayer.reset(); // Reset the media player to its uninitialized state
        }
    }
}