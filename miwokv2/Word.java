package com.example.android.miwokv2;

/**
 * Created by Sputnik on 9/15/2017.
 */

public class Word {
    private String mEnTranslation;
    private String mMiwokWord;
    private int mImageRes;
    private int mAudioRes;
    private boolean isPlaying;

    public Word(String miwok, String english, int imageRes, int audioRes) {
        mMiwokWord = miwok;
        mEnTranslation = english;
        mImageRes = imageRes;
        mAudioRes = audioRes;
        isPlaying = false;
    }

    public Word(String miwok, String english, int audioRes){
        mMiwokWord = miwok;
        mEnTranslation = english;
        mImageRes = 0;
        mAudioRes = audioRes;
        isPlaying = false;
    }

    public String getMiwok(){
        return mMiwokWord;
    }

    public String getEnTranslation() {
        return mEnTranslation;
    }

    public int getImageResource() {
        return mImageRes;
    }

    public int getAudioRes(){
        return mAudioRes;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean state){
        isPlaying = state;
    }
}