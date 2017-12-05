package com.example.mihovil.digitalnomad.files;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by Mihovil on 5.12.2017..
 */

public  class LoadingData{

    public static void EnableProgressBar(RelativeLayout relativeLayout, ProgressBar progressBar){
        relativeLayout.setAlpha(0.3f);
        progressBar.setVisibility(View.VISIBLE);
    }
    public static void DisableProgressBar(RelativeLayout relativeLayout, ProgressBar progressBar) {
        relativeLayout.setAlpha(1);
        progressBar.setVisibility(View.GONE);
    }
}

