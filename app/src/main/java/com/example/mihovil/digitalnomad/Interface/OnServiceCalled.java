package com.example.mihovil.digitalnomad.Interface;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by Mihovil on 7.12.2017..
 */

public interface OnServiceCalled {
    void EnableProgressBar(RelativeLayout relativeLayout, ProgressBar progressBar);
    void DisableProgressBar(RelativeLayout relativeLayout, ProgressBar progressBar);
}
