package com.curdrome.agenziaispjdm.research;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.curdrome.agenziaispjdm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends android.support.v4.app.Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

   JSONObject selection = new JSONObject();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ResearchActivity activity = (ResearchActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public class Residenziale {
        private HashMap<String, ArrayList<String>> list1;

        public Residenziale() {
            list1 = new HashMap<String, ArrayList<String>>();
            ArrayList<String> residenziale = new ArrayList<String>();
            residenziale.add("Appartamento");
            residenziale.add("Villa");
            residenziale.add("Monolocale");

        }

        public Collection<String> getResidenziale() {
            return list1.keySet();
        }

    }
    public class Commerciale {
        private HashMap<String, ArrayList<String>> list2;

        public Commerciale() {
            list2 = new HashMap<String, ArrayList<String>>();
            ArrayList<String> commerciale = new ArrayList<String>();
            commerciale.add("Ufficio");
            commerciale.add("Negozio");
            commerciale.add("Magazzino");

        }

        public Collection<String> getCommerciale() {
            return list2.keySet();
        }

    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = (Button)view.findViewById(R.id.idButtonSearch);


        RadioGroup subTypeRadioGroup = (RadioGroup)view.findViewById(R.id.subType);

        subTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb=(RadioButton) view.findViewById(checkedId);

                try {
                    selection.put("subtype", rb.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }




                }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("JSONObject APP!!!", selection.toString());
            }
        });
    }


}
