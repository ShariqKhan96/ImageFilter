package com.webxert.imagefilter.Interface;

/**
 * Created by hp on 8/18/2018.
 */

public interface BrushChangeListener {

    void onBrushOpacityChanged(int opacity);

    void onBrushSizeChanged(int size);

    void onBrushColorChanged(int color);

    void onBrushStateChanged(boolean isEraser);
}
