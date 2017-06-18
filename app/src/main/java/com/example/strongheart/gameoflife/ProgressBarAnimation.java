package com.example.strongheart.gameoflife;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;


/**
 * Created by strongheart on 6/12/17.
 */

public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float  to;
    private boolean isForward;
    private float value;

    public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
        isForward = true;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }

}
