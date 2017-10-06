package com.example.android.miwokv2;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

/**
 * Created by Sputnik on 9/21/2017.
 */

public class MyOnItemClickListener implements AdapterView.OnItemClickListener {

    private static Word currentWord;
    private static MyMediaPlayer currentMediaPlayer;
    private static BaseAdapter currentAdapter;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        final BaseAdapter adapter = (BaseAdapter) adapterView.getAdapter();
        final Word word = (Word) adapterView.getItemAtPosition(position);
        MyMediaPlayer myMediaPlayer = new MyMediaPlayer(view.getContext(), word.getAudioRes());
        if (word == currentWord & word.isPlaying()) {
            if (word.isPlaying()) {
                resetItem(currentAdapter, currentMediaPlayer, currentWord);
            } else {
                replayItem(adapter, myMediaPlayer, word);
                updateStaticMembers(adapter, myMediaPlayer, word);
            }
        } else {
            setListeners(adapter, myMediaPlayer, word);
            startItem(adapter, myMediaPlayer, word);
            updateStaticMembers(adapter, myMediaPlayer, word);
        }
    }

    private void updateStaticMembers(BaseAdapter adapter, MyMediaPlayer mediaPlayer, Word word) {
        currentAdapter = adapter;
        currentMediaPlayer = mediaPlayer;
        currentWord = word;
    }

    private void setListeners(final BaseAdapter adapter, MyMediaPlayer mediaPlayer, final Word word) {
        mediaPlayer.setMyOnFocusLostListener(new MyMediaPlayer.MyFocusLostListener() {
            @Override
            public void onMyFocusLost(MyMediaPlayer mediaPlayer) {
                mediaPlayer.removeMyOnCompletionListener();
                stopItem(adapter, mediaPlayer, word);
                word.setPlaying(false);
                mediaPlayer.release();

            }
        });
        mediaPlayer.setMyOnCompletionListener(new MyMediaPlayer.MyCompletionListener() {
            @Override
            public void onMyCompletion(MyMediaPlayer mediaPlayer) {
                stopItem(adapter, mediaPlayer, word);
                currentAdapter = null;
                currentMediaPlayer = null;
                currentWord = null;
            }
        });
    }

    private void stopItem(BaseAdapter adapter, MyMediaPlayer mediaPlayer, Word word) {
        word.setPlaying(false);
        adapter.notifyDataSetChanged();
        mediaPlayer.release();
    }

    private void startItem(BaseAdapter adapter, MyMediaPlayer mediaPlayer, Word word) {
        word.setPlaying(true);
        adapter.notifyDataSetChanged();
        mediaPlayer.start();
    }

    private void resetItem(BaseAdapter adapter, MyMediaPlayer mediaPlayer, Word word) {
        mediaPlayer.removeMyOnFocusLostListener();
        stopItem(adapter, mediaPlayer, word);
    }

    private void replayItem(final BaseAdapter adapter, MyMediaPlayer mediaPlayer, final Word word) {
        setListeners(adapter, mediaPlayer, word);
        word.setPlaying(true);
        adapter.notifyDataSetChanged();
        startItem(adapter, mediaPlayer, word);
    }

    public static void stopAll() {
        if (currentMediaPlayer != null) {
            currentMediaPlayer.release();
        }
        if (currentWord != null) {
            currentWord.setPlaying(false);
        }
        if (currentAdapter != null) {
            currentAdapter.notifyDataSetChanged();
        }
    }
}