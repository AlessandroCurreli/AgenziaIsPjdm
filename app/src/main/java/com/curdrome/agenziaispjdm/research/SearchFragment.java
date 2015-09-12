package com.curdrome.agenziaispjdm.research;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.curdrome.agenziaispjdm.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends android.support.v4.app.Fragment {

    ResearchActivity activity = (ResearchActivity) getActivity();
    Spinner residenziale;
    Spinner commerciale;
    ArrayAdapter <CharSequence>resideziale;
    ArrayAdapter<CharSequence>commerciale;

<<<<<<< Updated upstream
    ArrayList<String> selection=new ArrayList<>();
=======


   /* JSONObject selection={

            "Tipologia":"",
            "Prezzo":"",
            "Mq":""
    }
*/
>>>>>>> Stashed changes

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View viewSelection =inflater.inflate(R.layout.fragment_search, container, false);

        return viewSelection;
    }

    public class Residenziale
    {
        private HashMap<String,ArrayList<String>> list1;
        public Residenziale()
        {
            list1=new HashMap<String, ArrayList<String>>();
            ArrayList<String> residenziale=new ArrayList<String>();
            residenziale.add("Appartamento");
            residenziale.add("Villa");
            residenziale.add("Monolocale");

        }
        public Collection<String> getResidenziale()
        {
            return list1.keySet();
        }


        public class Commerciale
        {
            private HashMap<String,ArrayList<String>> list2;
            public Commerciale()
            {
                list2=new HashMap<String, ArrayList<String>>();
                ArrayList<String> commerciale=new ArrayList<String>();
                commerciale.add("Ufficio");
                commerciale.add("Negozio");
                commerciale.add("Magazzino");

            }
            public Collection<String> getCommerciale()
            {
                return list1.keySet();
            }


            public void select_item(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {

            case android.R.id.idBox:
                if (checked) {
                    selection.add("Box");
                }else{
                    selection.remove("Box");
                }
            case android.R.id.idTerreno:
                if(checked){
                    selection.add("Terreno");
                }else{
                    selection.remove("Terreno");
                }

            case android.R.id.idprezzo1:
                if (checked) {
                    selection.add("Fino a 100.000");
                } else {
                    selection.remove("Fino a 100.000");
                }
                break;
            case android.R.id.idPrezzo2:
                if (checked) {
                    selection.add("Da 100.000 a 300.000");
                } else {
                    selection.remove("Da 100.000 a 300.000");
                }
                break;
            case android.R.id.idPrezzo3:
                if (checked) {
                    selection.add("Oltre 300.000");
                } else {
                    selection.remove("Oltre 300.000");
                }
                break;
            case android.R.id.idMq1:
                if (checked) {
                    selection.add("Fino a 50mq");
                } else {
                    selection.remove("Fino a 50mq");
                }
                break;
            case android.R.id.idMq2:
                if (checked) {
                    selection.add("Da 50 a 100 mq");
                } else {
                    selection.remove("Da 50 a 100mq");
                }
                break;
            case android.R.id.idMq3:
                if (checked) {
                    selection.add("Da 100 a 200 mq");
                } else {
                    selection.remove("Da 100 a 200 mq");
                }
                break;
            case android.R.id.idMq4:
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

            Button btn = (Button)view.findViewById(android.R.id.idButtonSearch);

            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    select_item(view);

                }
            });
        }
        @Override
        public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
            View viewSelection =inflater.inflate(android.R.layout.activity_list_item, container, false);

            return viewSelection;


        }

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(android.R.layout.activity_search_main2);
            residenziale=(Spinner)findViewById(android.R.id.spinner1);
            commerciale = (Spinner)findViewById(android.R.id.spinner2);


            ArrayAdapter residenziale=ArrayAdapter.createFromResource(this, android.R.array.spinner_strings,android.R.layout.simple_spinner_item);
            residenziale.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            residenziale.setAdapter(residenziale);
            residenziale.setOnItemSelectedListener(this);
            ArrayAdapter commerciale=new ArrayAdapter.createFromResource(this,android.R.layout.simple_spinner_item);
            commerciale.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            commerciale.setAdapter(commerciale);
            commerciale.setOnItemSelectedListener(this);
        }


    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
