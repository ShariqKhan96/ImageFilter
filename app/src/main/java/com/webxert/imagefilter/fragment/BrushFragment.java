package com.webxert.imagefilter.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.webxert.imagefilter.Interface.BrushChangeListener;
import com.webxert.imagefilter.Interface.ColorPickListener;
import com.webxert.imagefilter.R;
import com.webxert.imagefilter.adapter.ColorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrushFragment extends BottomSheetDialogFragment implements ColorPickListener {

    RecyclerView recyclerView;
    SeekBar size_seekbar, opacity_seekbar;
    ToggleButton toggleButton;

    public static BrushFragment instance;

    public static BrushFragment getInstance() {
        if (instance == null)
            instance = new BrushFragment();
        return instance;
    }

    BrushChangeListener brushChangeListener;

    public void setBrushChangeListener(BrushChangeListener brushChangeListener) {
        this.brushChangeListener = brushChangeListener;
    }

    public BrushFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_brush, container, false);
        recyclerView = view.findViewById(R.id.color_recyclerview);
        size_seekbar = view.findViewById(R.id.size_seekbar);
        opacity_seekbar = view.findViewById(R.id.opacity_seekbar);
        toggleButton = view.findViewById(R.id.toggle_button);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        ColorAdapter adapter = new ColorAdapter(getContext(),  this);
        recyclerView.setAdapter(adapter);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                brushChangeListener.onBrushStateChanged(isChecked);
            }
        });

        size_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brushChangeListener.onBrushSizeChanged(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        opacity_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brushChangeListener.onBrushOpacityChanged(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;

    }



    @Override
    public void onColorSelect(int color) {
        brushChangeListener.onBrushColorChanged(color);
    }
}
