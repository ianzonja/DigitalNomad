package com.example.mihovil.digitalnomad.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.R;
import com.example.webservice.interfaces.ServiceResponse;
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

    public ReviewSystemFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.review_workspace,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        txtRating = (EditText) view.findViewById(R.id.txtRating);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = preferences.getString("Email", null);
                if (CheckEntry(ratingBar, txtRating)) {
                    WebServiceCaller wsc = new WebServiceCaller(ReviewSystemFragment.this);
                    System.out.println("mail: " + preferences.getString("Email", null) + " id: " + getArguments().getString("idWorkspace") + " rating: " + ratingBar.getRating() + " comment: " + txtRating.getText().toString());
                    wsc.uploadRatingAndComments(preferences.getString("Email", null), getArguments().getString("idWorkspace"), ratingBar.getRating(), txtRating.getText().toString());
                }
            }
        });
    }

    public void onServiceDone(Object response){
        ServiceResponse isSuccess = (ServiceResponse) response;
        System.out.println(isSuccess.getReturnValue());
        if (isSuccess.getReturnValue().equals("1")) {
            Toast.makeText(getContext(), "Uspjesno dodano", Toast.LENGTH_LONG).show();
        } else {
            Log.d("TAG", "ispostoje = false");
            Toast.makeText(getContext(), "Neuspjesno!", Toast.LENGTH_LONG).show();
        }
        Fragment fragment = new ShowReviewsFragment();
        Bundle valueBundle = new Bundle();
        valueBundle.putString("idWorkspace", getArguments().getString("idWorkspace"));
        fragment.setArguments(valueBundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    public void onServiceFail(Object message){
        Toast.makeText(getActivity(), "Error on web service!", Toast.LENGTH_SHORT).show();
    }

    private boolean CheckEntry(RatingBar rating, EditText comment){
        boolean success = true;
        if(rating.getRating() == 0){
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