package com.webxert.imagefilter.Interface;

/**
 * Created by hp on 8/6/2018.
 */

public interface EditPictureListener {

    void onBrightnessChanged(int brightness);
    void onConstrainedChanged(float constrained);
    void onSaturationChanged(float saturation);
    void onEditStarted();
    void onEditCompleted();


}
