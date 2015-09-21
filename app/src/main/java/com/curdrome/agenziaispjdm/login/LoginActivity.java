package com.curdrome.agenziaispjdm.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

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
    private JSONObject jo;
    private boolean fb;
    private CallbackManager callbackManager;
    private LoginButton loginButtonFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fb = false;

        connectionTask = new HttpAsyncTask();
        connectionTask.response = this;

        //instanziazione fragment con il bottone per il login con i dati di Facebook
        fragmentManager = getSupportFragmentManager();
        fTransaction = fragmentManager.beginTransaction();
        FragmentLogin lFragment = new FragmentLogin();

        fTransaction.add(R.id.frame_login, lFragment);
        fTransaction.commit();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        loginButtonFB = (LoginButton) findViewById(R.id.button_login_fb);
        loginButtonFB.setReadPermissions(Arrays.asList("public_profile, email"));
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
                                    //organizzazione dati in un JSON per l'invio dati
                                    jo = new JSONObject();
                                    jo.put("URL", R.string.login_url);
                                    //abilitazione flag bottone fb premuto, raccolta dati e invio dati
                                    Log.d("LoginFBRis", "" + email);
                                    fb = true;
                                    connectionTask.execute(jo);
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

        JSONObject jo = null;
        try {
            jo = new JSONObject(output);
            //controlli sulla risposta dal server
            //se nel JSON ricevuto non è presente il campo Status allora crea l'oggetto utente e
            //passa all'activity successiva
            if (!jo.has("status")) {
                user = new User();
                user = user.toJava(output);
                Intent intent = new Intent(LoginActivity.this, ResearchActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            } else {
                //in caso di errore viene comunque istanziata una nuova AsyncTask
                connectionTask = new HttpAsyncTask();
                connectionTask.response = this;
                switch (jo.getString("status")) {
                    case "success":
                        //replace fragment con login
                        Toast.makeText(getBaseContext(), getString(R.string.login_success), Toast.LENGTH_LONG).show();
                        RegisterFragment rFragment = new RegisterFragment();
                        fTransaction = fragmentManager.beginTransaction();
                        fTransaction.replace(R.id.frame_login, rFragment);
                        fTransaction.addToBackStack("fromRegister");
                        // Commit the transaction
                        fTransaction.commit();
                    case "not found":
                        Toast.makeText(getBaseContext(), getString(R.string.login_not_found), Toast.LENGTH_LONG).show();
                        break;

                    case "duplicate":
                        if (!fb) {
                            //in caso l'errore sia avvenuto in seguito a Login manuale

                            Toast.makeText(getBaseContext(), getString(R.string.register_duplicate), Toast.LENGTH_LONG).show();
                        } else {
                            //in caso di utente non trovato nel database i dati vengono riutilizzati per la registrazione
                            this.jo.put("URL", R.string.login_url);
                            connectionTask.execute(this.jo);
                        }
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //TODO controllo dati in locale per l'invio in automatico di Login e Password
    public boolean alreadyExisted(){

        return true;
    }

}
