package com.curdrome.agenziaispjdm.main;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends android.support.v4.app.Fragment {

    MainActivity activity;

    User user;

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

        mail.setText(user.getLogin(), TextView.BufferType.SPANNABLE);
        name.setText(user.getFirstname(), TextView.BufferType.SPANNABLE);
        surname.setText(user.getLastname(), TextView.BufferType.SPANNABLE);
        telefono.setText("" + user.getPhone(), TextView.BufferType.SPANNABLE);
        password.setText(user.getPassword(), TextView.BufferType.SPANNABLE);

    }
}
