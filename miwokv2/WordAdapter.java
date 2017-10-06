package com.example.android.miwokv2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sputnik on 9/15/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    static class ViewHolder {
        TextView miwok;
        TextView english;
        ImageView image;
        ImageView audioIcon;
    }

    public WordAdapter(Context context, List<Word> words) {
        super(context, 0, words);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        View listItemView = convertView;
        if (listItemView == null) {
            holder = new ViewHolder();
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.image_items_list, parent, false);
            holder.miwok = (TextView) listItemView.findViewById(R.id.miwok_number);
            holder.english = (TextView) listItemView.findViewById(R.id.english_number);
            holder.image = (ImageView) listItemView.findViewById(R.id.image);
            holder.audioIcon = (ImageView) listItemView.findViewById(R.id.icon_audio);
            listItemView.setTag(holder);
        } else {
            holder = (ViewHolder) listItemView.getTag();
        }

        //Get the {@link Word} object located at this position
        final Word currentWord = getItem(position);

        holder.miwok.setText(currentWord.getMiwok());
        holder.english.setText(currentWord.getEnTranslation());
        if (currentWord.isPlaying()) {
            holder.audioIcon.setImageResource(R.drawable.ic_stop_white_36dp);
        } else{
            holder.audioIcon.setImageResource(R.drawable.ic_play_arrow_white_36dp);
        }

        if (currentWord.getImageResource() == 0) {
            holder.image.setVisibility(View.GONE);
        } else {
            holder.image.setImageResource(currentWord.getImageResource());
        }

        return listItemView;
    }
}