package com.curdrome.agenziaispjdm.login;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.curdrome.agenziaispjdm.R;

import org.json.JSONException;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFBLogin extends android.support.v4.app.Fragment {


    public FragmentFBLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_fblogin, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LoginActivity activity = (LoginActivity) getActivity();
        Button loginButtonFb = (Button) view.findViewById(R.id.button_login_fb);
        try {
            TextView tvNameFb = (TextView) view.findViewById(R.id.tv_name_fb);
            tvNameFb.setText(activity.jo.getString("firstname"));
            TextView tvMailFb = (TextView) view.findViewById(R.id.tv_email_fb);
            tvNameFb.setText(activity.jo.getString("lastname"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final EditText password = (EditText) view.findViewById(R.id.pwd_fb_text);


        activity.loginButtonFB.setVisibility(View.INVISIBLE);

        loginButtonFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RegisterFragment.isValidPassword(password.getText().toString())) {
                    password.setError("password non valida");
                } else {
                    try {
                        activity.jo.put("password", password.getText().toString());
                        activity.connectionTask.execute(activity.jo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
