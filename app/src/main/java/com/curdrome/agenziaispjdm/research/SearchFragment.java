package com.curdrome.agenziaispjdm.research;


import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
        RadioGroup prezziRadioGroup = (RadioGroup)view.findViewById(R.id.radioGroupPrezzi);
        RadioGroup mqRadioGroup = (RadioGroup)view.findViewById(R.id.radioGroupMq);
        EditText bagniEditText = (EditText) view.findViewById(R.id.idNbagni);
        EditText camereEditText = (EditText) view.findViewById(R.id.idNcamere);

        subTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb=(RadioButton) view.findViewById(checkedId);

                String checkedValue =  rb.getText().toString();

                try {
                    selection.put("subtype", checkedValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        prezziRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) view.findViewById(checkedId);

                String checkedValue = rb.getText().toString();

                switch (checkedValue) {
                    case "fino a 100.000":
                        try {
                            selection.put("price_min", 0);
                            selection.put("price_max", 100000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "da 100.000 a 300.000":
                        try {
                            selection.put("price_min", 100000);
                            selection.put("price_max", 300000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "oltre 300.000":
                        try {
                            selection.put("price_min", 300000);
                            selection.put("price_max", 1000000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });

        mqRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) view.findViewById(checkedId);

                String checkedValue = rb.getText().toString();

                switch (checkedValue) {
                    case "fino a 50 mq":
                        try {
                            selection.put("sqm_min", 0);
                            selection.put("sqm_max", 50);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "da 50 a 100 mq":
                        try {
                            selection.put("sqm_min", 50);
                            selection.put("sqm_max", 100);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "da 100 a 200 mq":
                        try {
                            selection.put("sqm_min", 100);
                            selection.put("sqm_max", 200);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "oltre 200 mq":
                        try {
                            selection.put("sqm_min", 200);
                            selection.put("sqm_max", 1000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });

        bagniEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                try {
                    selection.put("bath", Integer.parseInt(s.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        camereEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                try {
                    selection.put("rooms",Integer.parseInt(s.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

            btn.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View view) {
                                           if (!selection.has("subtype")) {
                                               Toast.makeText(getActivity().getBaseContext(), "Seleziona una sottotipologia", Toast.LENGTH_LONG).show();
                                           }

                                           Log.d("AGENZIAISPJDM", selection.toString());
                                       }
                                   }

            );
        }
    }
