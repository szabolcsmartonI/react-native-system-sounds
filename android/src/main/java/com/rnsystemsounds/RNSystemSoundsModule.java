package com.rnsystemsounds;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.media.SoundPool;
import android.media.AudioAttributes;
import android.util.SparseIntArray;

public class RNSystemSoundsModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private SoundPool soundPool;
    private SparseIntArray soundMap;

    public RNSystemSoundsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        
        // SoundPool inicializálása
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        
        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();
        
        // SparseIntArray a hangok tárolásához
        soundMap = new SparseIntArray();
        
        // Példa: néhány rendszerhang betöltése
        loadSound(1, android.R.raw.notification_simple);
        loadSound(2, android.R.raw.notification);
        // További hangok betöltése igény szerint
    }

    private void loadSound(int soundID, int resourceId) {
        int loadedSoundId = soundPool.load(reactContext, resourceId, 1);
        soundMap.put(soundID, loadedSoundId);
    }

    @Override
    public String getName() {
        return "RNSystemSounds";
    }

    @ReactMethod
    public void playSystemSound(int soundID) {
        int loadedSoundId = soundMap.get(soundID, -1);
        if (loadedSoundId != -1) {
            soundPool.play(loadedSoundId, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    @ReactMethod
    public void stopSystemSound() {
        soundPool.autoPause();
    }
}
