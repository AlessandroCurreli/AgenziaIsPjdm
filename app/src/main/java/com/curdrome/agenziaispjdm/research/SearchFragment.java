package com.curdrome.agenziaispjdm.research;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

    JSONObject selection = new JSONObject();
    ResearchActivity activity;

    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button btn = (Button) view.findViewById(R.id.idButtonSearch);
        RadioGroup prezziRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroupPrezzi);
        RadioGroup mqRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroupMq);
        EditText bagniEditText = (EditText) view.findViewById(R.id.idNbagni);
        EditText camereEditText = (EditText) view.findViewById(R.id.idNcamere);

        final Spinner spinner1 = (Spinner) view.findViewById(R.id.propertiesTypes);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.types_array, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        final Spinner spinner2 = (Spinner) view.findViewById(R.id.propertiesSecondTypes);

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
                    selection.put("rooms", Integer.parseInt(s.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        spinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    selection.put("type", parent.getItemAtPosition(position).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (spinner1.getSelectedItem().equals("commerciale")) {
                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                            R.array.commerciale_array, android.R.layout.simple_spinner_dropdown_item);
                    // Specify the layout to use when the list of choices appears
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinner2.setAdapter(adapter2);
                } else if (spinner1.getSelectedItem().equals("residenziale")) {
                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                            R.array.residenziale_array, android.R.layout.simple_spinner_dropdown_item);
                    // Specify the layout to use when the list of choices appears
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinner2.setAdapter(adapter2);
                } else {
                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                            R.array.Empty, android.R.layout.simple_spinner_dropdown_item);
                    // Specify the layout to use when the list of choices appears
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinner2.setAdapter(adapter2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

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


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AGENZIAISPJDM", selection.toString());
                activity.searchConnection(selection);
            }
        });
    }
}