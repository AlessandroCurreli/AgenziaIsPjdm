package com.curdrome.agenziaispjdm.login;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.curdrome.agenziaispjdm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends android.support.v4.app.Fragment {

    private EditText emailText;
    private EditText nameText;
    private EditText surnameText;
    private EditText phoneText;
    private EditText passwordText;
    private JSONObject jo;
    private Button btRegister;

    public RegisterFragment() {
        // Required empty public constructor
    }

    // validating email id
    static protected boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    static protected boolean isValidPhone(String phone) {
        String PHONE_PATTERN = "^([+]39)?((38[{8,9}|0])|(34[{7-9}|0])|(36[6|8|0])|(33[{3-9}|0])|(32[{8,9}]))([\\d]{7})$";
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    // validating password with retype password
    static protected boolean isValidPassword(String pass) {
        String PWD_PATTERN = "\"((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})\";";
        Pattern pattern = Pattern.compile(PWD_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }

    static protected boolean isValidName(String name) {
        String NAME_PATTERN = "^((?:[A-Z](?:('|(?:[a-z]{1,3}))[A-Z])?[a-z]+)|(?:[A-Z]\\.))(?:([ -])((?:[A-Z](?:('|(?:[a-z]{1,3}))[A-Z])?[a-z]+)|(?:[A-Z]\\.)))?$";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LoginActivity activity = (LoginActivity) getActivity();

        activity.loginButtonFB.setVisibility(View.VISIBLE);

        emailText = (EditText) view.findViewById(R.id.email_text);
        nameText = (EditText) view.findViewById(R.id.name_text);
        surnameText = (EditText) view.findViewById(R.id.surname_text);
        phoneText = (EditText) view.findViewById(R.id.phone_text);
        passwordText = (EditText) view.findViewById(R.id.password_text);
        btRegister = (Button) view.findViewById(R.id.register_button);

        //gestione evento bottone premuto
        btRegister.setOnClickListener(new View.OnClickListener() {
            //ad evento avvenuto, vengono raccolti i dati ed
            //inviati al server per la registrazione
            @Override
            public void onClick(View v) {
                boolean validate = true;
                String email = emailText.getText().toString();
                if (!isValidEmail(email)) {
                    emailText.setError("Email non valida");
                    validate = false;
                }

                String password = null;
                try {

                    password = passwordText.getText().toString();

                } catch (Exception e) {
                    Log.d("agenziaIs", passwordText.getText().toString());
                    Log.d("agenziaIs", password);
                }
                if (!isValidPassword(password)) {
                    passwordText.setError("Password non valida");
                    validate = false;
                }
                String name = nameText.getText().toString();
                if (!isValidName(name)) {
                    nameText.setError("Nome non valido");
                    validate = false;
                }
                String surname = surnameText.getText().toString();
                if (!isValidName(surname)) {
                    nameText.setError("Nome non valido");
                    validate = false;
                }
                String sPhone = phoneText.getText().toString();
                double phone = Double.parseDouble(sPhone);
                if (!isValidPhone(sPhone)) {
                    phoneText.setError("Numero non valido");
                    validate = false;
                }
                if (validate) {
                    jo = new JSONObject();
                    email = emailText.getText().toString();
                    name = nameText.getText().toString();
                    surname = surnameText.getText().toString();
                    phone = Double.parseDouble(phoneText.getText().toString());
                    password = passwordText.getText().toString();
                    try {
                        jo.put("login", email);
                        jo.put("firstname", name);
                        jo.put("lastname", surname);
                        jo.put("email", email);
                        jo.put("phone", phone);
                        jo.put("password", password);
                        jo.put("URL", getString(R.string.register_url));

                        activity.connectionTask.execute(jo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
