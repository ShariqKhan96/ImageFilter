package com.webxert.imagefilter.fragment;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webxert.imagefilter.Interface.EmojiAdapterListener;
import com.webxert.imagefilter.Interface.EmojiListener;
import com.webxert.imagefilter.R;
import com.webxert.imagefilter.adapter.EmojiAdapter;

import ja.burhanrashid52.photoeditor.PhotoEditor;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmojiFragment extends BottomSheetDialogFragment implements EmojiAdapterListener {


    RecyclerView emojiRecylerview;
    EmojiListener listener;


    public void setListener(EmojiListener listener) {
        this.listener = listener;
    }

    static EmojiFragment instance;

    public static EmojiFragment getInstance() {
        if (instance == null)
            instance = new EmojiFragment();
        return instance;
    }

    public EmojiFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emoji, container, false);

        emojiRecylerview = view.findViewById(R.id.emoji_recycler);
        emojiRecylerview.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        EmojiAdapter adapter = new EmojiAdapter(getContext(), PhotoEditor.getEmojis(getContext()), this);
        emojiRecylerview.setAdapter(adapter);


        return view;

    }

    @Override
    public void onEmojiitemSelected(String emoji) {
        listener.onEmojiSelected(emoji);
    }
}
