package com.curdrome.agenziaispjdm.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.connection.AsyncResponse;
import com.curdrome.agenziaispjdm.connection.HttpAsyncTask;
import com.curdrome.agenziaispjdm.model.User;
import com.curdrome.agenziaispjdm.research.ResearchActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends FragmentActivity implements AsyncResponse {

    protected User user;
    protected HttpAsyncTask connectionTask;
    protected FragmentManager fragmentManager;
    protected FragmentTransaction fTransaction;
    private CallbackManager callbackManager;
    private LoginButton loginButtonFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connectionTask = new HttpAsyncTask();
        connectionTask.response = this;

        //instanziazione fragment con il bottone per il login con i dati di Facebook
        fragmentManager = getSupportFragmentManager();
        fTransaction = fragmentManager.beginTransaction();
        FragmentLogin lFragment = new FragmentLogin();
        //RegisterFragment rFragment = new RegisterFragment();

        fTransaction.add(R.id.frame_login, lFragment);
        fTransaction.commit();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        loginButtonFB = (LoginButton) findViewById(R.id.button_login_fb);
        loginButtonFB.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        loginButtonFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("AgLogFB", "Successful Login");

                GraphRequest meRequest = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String email = response.getJSONObject().getString("email").toString();
                                    Log.d("LoginFBRis", "" + email);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, gender");
                meRequest.setParameters(parameters);
                meRequest.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d("AgLogFB", "Login Canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("AgLogFB", "Login Error");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // manage login results
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //override del metodo per la gestione dei risultati dell'AsyncTask
    @Override
    public void taskResult(String output) {

        //TODO controllo sui risultati e in caso negativo istanzio nuova AsyncTask
        //converto il risultato ad oggetto user
        user = new User();
        user = user.toJava(output);
        Intent intent = new Intent(LoginActivity.this, ResearchActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();

    }

    //TODO controllo dati in locale per l'invio in automatico di Login e Password
    public boolean alreadyExisted(){

        return true;
    }

}
