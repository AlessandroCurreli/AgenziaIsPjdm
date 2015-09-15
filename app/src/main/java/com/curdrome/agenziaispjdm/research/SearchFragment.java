package com.curdrome.agenziaispjdm.research;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.curdrome.agenziaispjdm.R;

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

    ArrayList<String> selection = new ArrayList<>();

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
    public void select_item(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {

            case R.id.idBox:
                if (checked) {
                    selection.add("Box");
                }else{
                    selection.remove("Box");
                }
            case R.id.idTerreno:
                if(checked){
                    selection.add("Terreno");
                }else{
                    selection.remove("Terreno");
                }

            case R.id.idprezzo1:
                if (checked) {
                    selection.add("Fino a 100.000");
                } else {
                    selection.remove("Fino a 100.000");
                }
                break;
            case R.id.idPrezzo2:
                if (checked) {
                    selection.add("Da 100.000 a 300.000");
                } else {
                    selection.remove("Da 100.000 a 300.000");
                }
                break;
            case R.id.idPrezzo3:
                if (checked) {
                    selection.add("Oltre 300.000");
                } else {
                    selection.remove("Oltre 300.000");
                }
                break;
            case R.id.idMq1:
                if (checked) {
                    selection.add("Fino a 50mq");
                } else {
                    selection.remove("Fino a 50mq");
                }
                break;
            case R.id.idMq2:
                if (checked) {
                    selection.add("Da 50 a 100 mq");
                } else {
                    selection.remove("Da 50 a 100mq");
                }
                break;
            case R.id.idMq3:
                if (checked) {
                    selection.add("Da 100 a 200 mq");
                } else {
                    selection.remove("Da 100 a 200 mq");
                }
                break;
            case R.id.idMq4:
                if (checked) {
                    selection.add("Oltre 200 mq");
                } else {
                    selection.remove("Oltre 200 mq");
                }
                break;

        };}


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = (Button)view.findViewById(R.id.idButtonSearch);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                select_item(view);

            }
        });
    }


}
