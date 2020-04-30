package com.example.mycoronavirusapp.ui.prevention;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mycoronavirusapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreventionFragment extends Fragment {


    public PreventionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_prevention, container, false);
        
        return root;
    }

}
