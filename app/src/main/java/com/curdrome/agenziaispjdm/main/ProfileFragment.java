package com.curdrome.agenziaispjdm.main;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.login.RegisterFragment;
import com.curdrome.agenziaispjdm.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends android.support.v4.app.Fragment {

    private MainActivity activity;

    private User user;

    private EditText emailText;
    private EditText nameText;
    private EditText surnameText;
    private EditText phoneText;
    private EditText passwordText;
    private EditText newPasswordText;
    private JSONObject jo = new JSONObject();
    private Button applyButton;

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

        emailText = (EditText) view.findViewById(R.id.email_text);
        nameText = (EditText) view.findViewById(R.id.name_text);
        surnameText = (EditText) view.findViewById(R.id.surname_text);
        phoneText = (EditText) view.findViewById(R.id.phone_text);
        passwordText = (EditText) view.findViewById(R.id.password_text);
        newPasswordText = (EditText) view.findViewById(R.id.new_password_text);

        applyButton = (Button) view.findViewById(R.id.apply_button);

        emailText.setText(user.getLogin(), TextView.BufferType.EDITABLE);
        nameText.setText(user.getFirstname(), TextView.BufferType.EDITABLE);
        surnameText.setText(user.getLastname(), TextView.BufferType.EDITABLE);
        phoneText.setText(String.format("%s", (long) user.getPhone()), TextView.BufferType.EDITABLE);

        try {
            jo.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean validate = true;

                String email = emailText.getText().toString();
                if (!RegisterFragment.isValidEmail(email)) {
                    emailText.setError("Email non valida");
                    validate = false;
                }

                String newPassword = null;

                String password = passwordText.getText().toString();

                if (!password.isEmpty())
                    if (!RegisterFragment.isValidPassword(password) && !password.equals(user.getPassword())) {
                        passwordText.setError("Password non valida");
                        validate = false;
                    } else {
                        newPassword = newPasswordText.getText().toString();
                        if (!RegisterFragment.isValidPassword(newPassword)) {
                            passwordText.setError("Nuova password non valida");
                            validate = false;
                        }
                    }
                String name = nameText.getText().toString();
                if (!RegisterFragment.isValidName(name)) {
                    nameText.setError("Nome non valido");
                    validate = false;
                }
                String surname = surnameText.getText().toString();
                if (!RegisterFragment.isValidSurname(surname)) {
                    surnameText.setError("Cognome non valido");
                    validate = false;
                }
                String sPhone = phoneText.getText().toString();
                if (!RegisterFragment.isValidPhone(sPhone)) {
                    phoneText.setError("Numero non valido");
                    validate = false;
                }
                if (validate) {
                    jo = new JSONObject();
                    email = emailText.getText().toString();
                    name = nameText.getText().toString();
                    surname = surnameText.getText().toString();
                    double phone = Double.parseDouble(phoneText.getText().toString());
                    password = passwordText.getText().toString();
                    newPassword = newPasswordText.getText().toString();
                    try {
                        jo.put("id", user.getId());
                        jo.put("login", email);
                        jo.put("firstname", name);
                        jo.put("lastname", surname);
                        jo.put("email", email);
                        jo.put("phone", phone);
                        if (password.isEmpty())
                            jo.put("password", user.getPassword());
                        else
                            jo.put("password", password);
                        if (password.equals(user.getPassword())) {
                            jo.put("new_password", newPassword);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    activity.updateUserConnection(jo);

                }
            }
        });

    }
}
