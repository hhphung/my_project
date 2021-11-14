package com.example.meetme.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.meetme.R;

import com.example.meetme.api.UserApi;

import static com.example.meetme.api.apiClientFactory.GetUserApi;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DayAvailabilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayAvailabilityFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "day";
    private static final String ARG_PARAM2 = "username";

    private String username;
    private int day;
    private View view;

    private static final Boolean[] weeklyAvailability = new Boolean[168];


    public DayAvailabilityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1
     * @return A new instance of fragment DayAvailabilityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DayAvailabilityFragment newInstance(int param1) {
        DayAvailabilityFragment fragment = new DayAvailabilityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_day_availability, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Confirms each day's availability and posts it to the server at end of the activity.
     * @return
     * True if the user has input their availability for the last day of the week. False otherwise
     */
    public boolean confirmDayAvailability(){
        Resources res = getResources();
        
        for (Integer i = 0; i < 24; i++){
            String buttonId = "fragment_availability_" + i.toString();
            int id = res.getIdentifier(buttonId, "id", getContext().getPackageName());
            RadioButton b = view.findViewById(id);
            if (b.isChecked()){
                weeklyAvailability[i+day*24] = true;
            }
        }

        if (day == 6){
            //TODO implement the post method here
            GetUserApi().sendAvailability(username, weeklyAvailability);

            Intent myIntent = new Intent(view.getContext(), DashboardPage.class);
            myIntent.putExtra("username", username);
            startActivity(myIntent);
            return true;
        }
        return false;
    }
}