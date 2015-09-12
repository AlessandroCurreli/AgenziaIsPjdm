package com.curdrome.agenziaispjdm.research;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curdrome.agenziaispjdm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends android.support.v4.app.Fragment {


    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }


}
