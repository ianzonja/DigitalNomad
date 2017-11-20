package com.example.mihovil.digitalnomad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ian on 11/18/2017.
 */

public class CardView_Activity extends Activity{
    TextView personName;
    TextView personAge;
    ImageView personPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_view_);
        personName = (TextView)findViewById(R.id.workspace_name);
        personAge = (TextView)findViewById(R.id.workspace_age);
        personPhoto = (ImageView)findViewById(R.id.person_photo);

        personName.setText("Emma Wilson");
        personAge.setText("23 years old");
    }
}
