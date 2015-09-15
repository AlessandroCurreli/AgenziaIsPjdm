package com.curdrome.agenziaispjdm.research;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

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
    public JSONObject fields(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch(R.id.subType) {
            case R.id.idTerreno:
                if (checked) {
                    try {
                        selection.put("subtype", "terreno");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.idBox:
                if (checked) {
                    try {
                        selection.put("subtype", "box");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

        return selection;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = (Button)view.findViewById(R.id.idButtonSearch);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                JSONObject jo = fields(view);
                Log.d("JSONObject APP!!!",jo.toString());

            }
        });
    }


}
