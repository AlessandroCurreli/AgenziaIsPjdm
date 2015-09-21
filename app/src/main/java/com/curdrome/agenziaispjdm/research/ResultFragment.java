package com.curdrome.agenziaispjdm.research;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.adapters.ResultsAdapter;
import com.curdrome.agenziaispjdm.model.Property;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends android.support.v4.app.Fragment {

    ResearchActivity activity;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ResearchActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Property> listResults = activity.getPropertiesResult();


        final ListView mylist = (ListView) view.findViewById(R.id.results);
        final ResultsAdapter adapter = new ResultsAdapter(getActivity().getBaseContext(),
                R.layout.custom_row, listResults);
        mylist.setAdapter(adapter);

    }
}
