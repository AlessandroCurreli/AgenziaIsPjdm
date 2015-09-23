package com.curdrome.agenziaispjdm.main;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends android.support.v4.app.Fragment {

    private MainActivity activity;

    private User user;

    private JSONObject jsonObject = new JSONObject();

    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity.getActionBar().setTitle(R.string.title_profile_fragment);

        user = activity.getUser();

        EditText mail = (EditText) view.findViewById(R.id.email_text);
        EditText name = (EditText) view.findViewById(R.id.name_text);
        EditText surname = (EditText) view.findViewById(R.id.surname_text);
        EditText telefono = (EditText) view.findViewById(R.id.phone_text);
        EditText password = (EditText) view.findViewById(R.id.password_text);
        final EditText newPassword = (EditText) view.findViewById(R.id.new_password_text);

        Button applyButton = (Button) view.findViewById(R.id.apply_button);

        mail.setText(user.getLogin(), TextView.BufferType.EDITABLE);
        name.setText(user.getFirstname(), TextView.BufferType.EDITABLE);
        surname.setText(user.getLastname(), TextView.BufferType.EDITABLE);
        telefono.setText("" + user.getPhone(), TextView.BufferType.EDITABLE);

        try {
            jsonObject.put("id", user.getId());
            jsonObject.put("mail", user.getLogin());
            jsonObject.put("firstname", user.getFirstname());
            jsonObject.put("lastname", user.getLastname());
            jsonObject.put("phone", user.getPhone());
            jsonObject.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                user.setLogin(s.toString());
                user.setEmail(s.toString());
                try {
                    jsonObject.put("mail", s.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                user.setFirstname(s.toString());
                try {
                    jsonObject.put("firstname", s.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                user.setLastname(s.toString());
                try {
                    jsonObject.put("lastname", s.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        telefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                user.setPhone(Double.parseDouble(s.toString()));
                try {
                    jsonObject.put("phone", Double.parseDouble(s.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (user.getPassword().equals(s.toString())) {
                    Toast.makeText(activity.getBaseContext(), "Password coretta, sei abilitato a cambiarla", Toast.LENGTH_LONG).show();
                    newPassword.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            try {
                                jsonObject.put("new_password", s.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    Toast.makeText(activity.getBaseContext(), "Password errata, non sei abilitato a cambiarla", Toast.LENGTH_LONG).show();
                }

            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.updateUserConnection(jsonObject);
            }
        });

    }
}
