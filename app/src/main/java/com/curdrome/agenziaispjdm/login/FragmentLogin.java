package com.curdrome.agenziaispjdm.login;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.curdrome.agenziaispjdm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends android.support.v4.app.Fragment {

    public FragmentLogin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginActivity activity = (LoginActivity) getActivity();

        //EditText email = (EditText)activity.findViewById(R.id.email_text);
        //EditText password = (EditText)activity.findViewById(R.id.pwd_text);

        //bottone per il login
        //Button btLogin = (Button)activity.findViewById(R.id.button_login);

        //btLogin.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //   public void onClick(View view) {
        //se i dati inseriti passano la "valutazione"
        //       if(validate(email.getText().toString(), email.getText().toString())){
        //           activity.loginConnection(email.getText().toString(), password.getText().toString());
        //       }
        //   }
        //});

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
        final EditText email = (EditText)view.findViewById(R.id.email_text);
        final EditText password = (EditText)view.findViewById(R.id.pwd_text);
        final TextView tv = (TextView) view.findViewById(R.id.tv_warnings);
        //bottone per il login
        Button btLogin = (Button)view.findViewById(R.id.button_login);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity activity = (LoginActivity) getActivity();
                //se i dati inseriti passano la "valutazione"
                //if(validate(email.getText().toString(), password.getText().toString())){
                activity.loginConnection(email.getText().toString(), password.getText().toString());
                //}
            }
        });

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
        public void onFragmentInteraction(Uri uri);
    }

    //validazione campi email e password
    private boolean validate(String email, String password){
        if(email.equals(""))
            return false;
        else if(password.equals(""))
            return false;
        else
            return true;
    }

}
