package com.example.mihovil.digitalnomad.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

/**
 * Created by Davor on 9.1.2018..
 */

public class ReviewSystemFragment extends Fragment implements OnServiceFinished {

    private RatingBar ratingBar;
    private EditText txtRating;
    private TextView txtRatingValue;
    private Button btnSubmit;
    private SharedPreferences preferences;
    Workspace workspaces;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.review_workspace,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        txtRating = (EditText) view.findViewById(R.id.txtRating);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = preferences.getString("Email", null);
                String id = workspaces.id;

                if (CheckEntry(ratingBar, txtRating)) {
                    WebServiceCaller wsc = new WebServiceCaller(ReviewSystemFragment.this);
                    wsc.uploadRatingAndComments(email, id, ratingBar.getRating(), txtRating.getText().toString());
                }
            }
        });
    }

    public void onServiceDone(Object response){
        Toast.makeText(getActivity(), "Data stored", Toast.LENGTH_SHORT).show();
    }

    public void onServiceFail(Object message){
        Toast.makeText(getActivity(), "Error on web service!", Toast.LENGTH_SHORT).show();
    }

    private boolean CheckEntry(RatingBar rating, EditText comment){
        boolean success = true;
        if(rating.getRating() == 0){
            rating.setRating(1);
            success = false;
        }

        if(comment.getText().toString().isEmpty()){
            comment.setError("Enter your comment!");
            success = false;
        }
        else{
            comment.setError(null);
        }

        return success;
    }
}
