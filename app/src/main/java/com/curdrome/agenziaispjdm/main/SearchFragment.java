package com.curdrome.agenziaispjdm.main;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.curdrome.agenziaispjdm.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends android.support.v4.app.Fragment {

    // json che contiene tutti i campi scelti
    JSONObject selection = new JSONObject();
    // collegamento alla activity
    MainActivity activity;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            selection.put("type", "commerciale");
            selection.put("subtype", "ufficio");
            selection.put("price_min", 0);
            selection.put("price_max", 100000);
            selection.put("sqm_min", 0);
            selection.put("sqm_max", 50);
            selection.put("rooms", 1);
            selection.put("bath", 1);
            selection.put("province", "AG");
            selection.put("city", "Agrigento");
            selection.put("zone", "none");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // redio group per la gestione della scelta della fascia di prezzo
        RadioGroup prezziRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroupPrezzi);

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

        //radio group per la scelta della fascia dei metri quadri

        RadioGroup mqRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroupMq);

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

        // gestione della edit text per il numero di bagni

        EditText bagniEditText = (EditText) view.findViewById(R.id.idNbagni);

        bagniEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                try {
                    if (s.length() != 0)
                        selection.put("bath", Integer.parseInt(s.toString()));
                    else
                        selection.put("bath", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // gestione della edit text per la gestione del numero delle camere

        EditText camereEditText = (EditText) view.findViewById(R.id.idNcamere);

        camereEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                try {
                    if (s.length() != 0)
                        selection.put("rooms", Integer.parseInt(s.toString()));
                    else
                        selection.put("roms", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // spinner per la scelta del tipo

        final Spinner spinnerTypes = (Spinner) view.findViewById(R.id.propertiesTypes);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.types_array, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTypes.setAdapter(adapter1);

        // spinner condizionato per la scelta del sottotipo

        final Spinner spinnerSubtype = (Spinner) view.findViewById(R.id.propertiesSecondTypes);

        spinnerTypes.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selection.put("type", parent.getItemAtPosition(position).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (spinnerTypes.getSelectedItem().equals("commerciale")) {
                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                            R.array.commerciale_array, android.R.layout.simple_spinner_dropdown_item);
                    // Specify the layout to use when the list of choices appears
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinnerSubtype.setAdapter(adapter2);
                } else if (spinnerTypes.getSelectedItem().equals("residenziale")) {
                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                            R.array.residenziale_array, android.R.layout.simple_spinner_dropdown_item);
                    // Specify the layout to use when the list of choices appears
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinnerSubtype.setAdapter(adapter2);
                } else {
                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                            R.array.Empty, android.R.layout.simple_spinner_dropdown_item);
                    // Specify the layout to use when the list of choices appears
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinnerSubtype.setAdapter(adapter2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // spinner to choose zone
        final Spinner spinnerZone = (Spinner) view.findViewById(R.id.zone);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.Empty, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerZone.setAdapter(adapter3);

        spinnerSubtype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selection.put("subtype", parent.getItemAtPosition(position).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // spinner per la scelta della provincia

        final Spinner spinnerProvincia = (Spinner) view.findViewById(R.id.provincia);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapterProvincia = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.Province, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapterProvincia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerProvincia.setAdapter(adapterProvincia);

        // spinner per la scelta della citta

        final Spinner spinnerCitta = (Spinner) view.findViewById(R.id.citta);

        spinnerProvincia.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selection.put("province", parent.getItemAtPosition(position).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int[] arrayCitta = {R.array.AG, R.array.AL, R.array.AN, R.array.BA, R.array.BG, R.array.BO, R.array.CT, R.array.FI, R.array.GE, R.array.MI, R.array.NA, R.array.PA, R.array.PG, R.array.RC, R.array.TO, R.array.TN, R.array.VE};

                ArrayAdapter<CharSequence> adapterCitta = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                        arrayCitta[position], android.R.layout.simple_spinner_dropdown_item);
                // Specify the layout to use when the list of choices appears
                adapterCitta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinnerCitta.setAdapter(adapterCitta);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // bottone di conferma che invia il json creato alla asyncTask

        final Button btn = (Button) view.findViewById(R.id.idButtonSearch);

        btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                activity.searchConnection(selection);
            }
        });

    }
}