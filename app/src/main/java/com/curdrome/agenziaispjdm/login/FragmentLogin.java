package com.curdrome.agenziaispjdm.login;


import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.curdrome.agenziaispjdm.R;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends android.support.v4.app.Fragment {

    private EditText email;
    private EditText password;
    private JSONObject jo;
    private LoginActivity activity;

    public FragmentLogin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginManager.getInstance().logOut();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (LoginActivity) getActivity();
        activity.loginButtonFB.setVisibility(View.VISIBLE);
        email = (EditText) view.findViewById(R.id.email_text);
        password = (EditText) view.findViewById(R.id.pwd_text);
        //bottone per il login
        Button btLogin = (Button) view.findViewById(R.id.button_login);

        Button btRegister = (Button) view.findViewById(R.id.register_button);

        if (!activity.isConnected()) {
            Toast.makeText(activity.getBaseContext(), "Il dispositivo è offline", Toast.LENGTH_SHORT).show();
            btLogin.setEnabled(false);
            btRegister.setEnabled(false);
        }

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RegisterFragment.isValidEmail(email.getText().toString())) {
                    email.setError("e-mail non valida");
                }
                if (!RegisterFragment.isValidPassword(password.getText().toString())) {
                    password.setError("password non valida");
                }
                if ((RegisterFragment.isValidEmail(email.getText().toString()) && (RegisterFragment.isValidPassword(password.getText().toString())))) {

                    jo = new JSONObject();
                    try {
                        jo.put("login", email.getText().toString());
                        jo.put("password", password.getText().toString());
                        jo.put("URL", getString(R.string.login_url));
                        activity.connectionTask.execute(jo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment rFragment = new RegisterFragment();
                activity.fTransaction = activity.fragmentManager.beginTransaction();
                activity.fTransaction.replace(R.id.frame_login, rFragment);
                activity.fTransaction.addToBackStack("fromLogin");
                // Commit the transaction
                activity.fTransaction.commit();
            }
        });

        //activity.loginButtonFB = (LoginButton) view.findViewById(R.id.button_login_fb);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
