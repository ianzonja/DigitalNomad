package com.mihovil.advancedsearch;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mihovil.advancedsearch.interfaces.OnAdvancedSearch;

import org.w3c.dom.Text;

/**
 * Created by Mihovil on 20.1.2018..
 */

public class advancedSearchFragment extends Fragment implements View.OnClickListener {

    private CheckBox accomodation;
    private CheckBox food;
    private CheckBox wifi;
    private CheckBox socialActivities;
    private CheckBox aZ;
    private OnAdvancedSearch listener;
    private Button filterOut;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity mainMenu = (Activity) context;
        listener = (OnAdvancedSearch) mainMenu;
        filterOut.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.napredno_pretrazivanje, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        accomodation = view.findViewById(R.id.checkbox_accommodation);
        food = view.findViewById(R.id.checkbox_food);
        wifi = view.findViewById(R.id.checkbox_wifi);
        socialActivities = view.findViewById(R.id.checkbox_social_activities);
        aZ = view.findViewById(R.id.checkbox_az);
        filterOut = view.findViewById(R.id.btnSearch);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnSearch) {
            listener.onAdvancedResult(accomodation.isChecked(),food.isChecked(),wifi.isChecked(),socialActivities.isChecked(),aZ.isChecked());
        }
    }
}
