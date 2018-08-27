package com.webxert.imagefilter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webxert.imagefilter.Interface.EmojiAdapterListener;
import com.webxert.imagefilter.R;

import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconTextView;

/**
 * Created by hp on 8/20/2018.
 */

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder> {
    Context context;
    List<String> emojiList;
    EmojiAdapterListener listener;

    public EmojiAdapter(Context context, List<String> emojiList, EmojiAdapterListener listener) {
        this.context = context;
        this.emojiList = emojiList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmojiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmojiViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emoji_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiViewHolder holder, int position) {
        holder.emojiconTextView.setText(emojiList.get(position));

    }

    @Override
    public int getItemCount() {
        return emojiList.size();
    }

    public class EmojiViewHolder extends RecyclerView.ViewHolder {

        EmojiconTextView emojiconTextView;


        public EmojiViewHolder(View itemView) {
            super(itemView);
            emojiconTextView = itemView.findViewById(R.id.emoji_textview);

            emojiconTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEmojiitemSelected(emojiList.get(getAdapterPosition()));
                }
            });
        }
    }
}
