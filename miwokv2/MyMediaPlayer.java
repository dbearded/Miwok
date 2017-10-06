package com.example.android.miwokv2;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

/**
 * Created by Sputnik on 9/21/2017.
 */

public class MyMediaPlayer implements MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {

    private MediaPlayer mediaPlayer;

    private MyCompletionListener completionListener;
    private MyFocusLostListener focusLostListener;
    private AudioManager audioManager;

    public interface MyCompletionListener {
        void onMyCompletion(MyMediaPlayer mediaPlayer);
    }

    public interface MyFocusLostListener {
        void onMyFocusLost(MyMediaPlayer mediaPlayer);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            // stop and reset all playback
            focusLostListener.onMyFocusLost(this);
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
            // stop and reset all playback
            focusLostListener.onMyFocusLost(this);
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
            // stop and reset all playback
            focusLostListener.onMyFocusLost(this);
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
            // Do nothing since I'm not restoring playback.
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();
        completionListener.onMyCompletion(this);
    }

    public MyMediaPlayer(Context context, int resId) {
        mediaPlayer = MediaPlayer.create(context, resId);
        mediaPlayer.setOnCompletionListener(this);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        abandonAudioFocus();
    }

    public void setMyOnCompletionListener(MyCompletionListener listener) {
        this.completionListener = listener;
    }

    public void removeMyOnCompletionListener(){
        this.completionListener = null;
    }

    public void setMyOnFocusLostListener(MyFocusLostListener listener) {
        this.focusLostListener = listener;
    }

    public void removeMyOnFocusLostListener(){
        this.focusLostListener = null;
    }

    private void abandonAudioFocus(){
        audioManager.abandonAudioFocus(this);
    }

    public void start() {
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer.start();
        }
    }
}