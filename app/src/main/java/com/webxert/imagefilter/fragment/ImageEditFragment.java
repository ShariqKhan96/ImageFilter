package com.webxert.imagefilter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.webxert.imagefilter.Interface.EditPictureListener;
import com.webxert.imagefilter.Interface.EditPressedListener;
import com.webxert.imagefilter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageEditFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private EditPictureListener editListener;

    private EditPressedListener editPressedListener;
    private SeekBar brightness_seekbar, contrast_seekbar, saturation_seekbar;


    public ImageEditFragment() {
        // Required empty public constructor
    }


    public void setEditListener(EditPictureListener editListener) {
        this.editListener = editListener;
    }

    public void setEditPressedListener(EditPressedListener editPressedListener) {
        this.editPressedListener = editPressedListener;
        //this.editPressedListener.onEditPressed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_edit, container, false);
        brightness_seekbar = view.findViewById(R.id.seekbar_brightness);
        contrast_seekbar = view.findViewById(R.id.seekbar_constraint);
        saturation_seekbar = view.findViewById(R.id.seekbar_saturation);

        brightness_seekbar.setMax(200);
        brightness_seekbar.setProgress(100);

        contrast_seekbar.setMax(20);
        contrast_seekbar.setProgress(0);

        saturation_seekbar.setMax(30);
        saturation_seekbar.setProgress(10);

        saturation_seekbar.setOnSeekBarChangeListener(this);
        contrast_seekbar.setOnSeekBarChangeListener(this);
        brightness_seekbar.setOnSeekBarChangeListener(this);

        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (editListener != null) {
            if (seekBar.getId() == R.id.seekbar_brightness) {
                editListener.onBrightnessChanged(progress - 100);

            } else if (seekBar.getId() == R.id.seekbar_constraint) {
                progress += 10;
                float value = .10f * progress;
                editListener.onConstrainedChanged(value);
            } else {
                float value = .10f * progress;
                editListener.onSaturationChanged(value);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (editListener != null)
            editListener.onEditStarted();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (editListener != null)
            editListener.onEditCompleted();
    }

    public void restControls() {
        saturation_seekbar.setProgress(10);
        contrast_seekbar.setProgress(0);
        brightness_seekbar.setProgress(100);
    }
}
