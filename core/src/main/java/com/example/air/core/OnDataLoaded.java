package com.example.air.core;

        import android.graphics.Bitmap;

        import com.example.mihovil.digitalnomad.models.Workspace;

        import java.util.ArrayList;

/**
 * Created by Ian on 1/25/2018.
 */

public interface OnDataLoaded {
    void onDataLoaded(ArrayList<Workspace> workspaces, Bitmap[] workspaceBitmaps);
}
