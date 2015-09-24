package com.curdrome.agenziaispjdm.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.connection.AsyncResponse;
import com.curdrome.agenziaispjdm.connection.HttpAsyncTask;
import com.curdrome.agenziaispjdm.main.MainActivity;
import com.curdrome.agenziaispjdm.model.User;
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
    protected JSONObject jo;
    protected LoginButton loginButtonFB;
    private boolean fb;
    private CallbackManager callbackManager;
    private Fragment fragment;

    //salvataggio fragment in Bundle
    @Override
    public void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, "fragment", fragment);
        super.onSaveInstanceState(outState);
    }

    //recupero fragment dal Bundle
    @Override
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        //se il Bundle esiste, allora viene recuperato il fragment precedente altrimenti ne viene instanziato uno nuovo
        if (inState != null) {
            fragment = getSupportFragmentManager().getFragment(inState, "fragment");
        } else fragment = new FragmentLogin();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = new User();
        fb = false;
        connectionTask = new HttpAsyncTask();
        connectionTask.response = this;

        if (!user.loadData(getApplicationContext()).equals("")) {
            try {
                jo = new JSONObject(user.loadData(getApplicationContext()));
                jo.put("URL", getString(R.string.login_url));
                connectionTask.execute(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        setContentView(R.layout.activity_login);

        jo = new JSONObject();

        //instanziazione fragment con il bottone per il login con i dati di Facebook
        fragmentManager = getSupportFragmentManager();
        fTransaction = fragmentManager.beginTransaction();
        //TODO se non funziona direttamente così provare con replace (magari spostando l'instanziamento del fragment di linea 81 qui e fare un.add nel caso di fragment null o un .replace altrimenti)
        if (fragment != null) {
            fTransaction.replace(R.id.frame_login, fragment);
        } else {
            fragment = new FragmentLogin();
            fTransaction.add(R.id.frame_login, fragment);
        }
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
                                    //memorizzazione dati nel JSON
                                    jo.put("login", object.getString("email"));
                                    jo.put("firstname", object.getString("first_name"));
                                    jo.put("lastname", object.getString("last_name"));
                                    jo.put("email", object.getString("email"));
                                    jo.put("phone", 0);
                                    jo.put("URL", getString(R.string.register_url));
                                    //abilitazione flag bottone fb premuto, raccolta dati
                                    Log.d("LoginFBRis", "" + object.getString("email") + "");
                                    Log.d("LoginFBRis", "" + object.toString() + "");
                                    fb = true;
                                    //dopo la raccolta dati viene visualizzato il fragment per l'inserimento della password,
                                    //necessaria tanto per il login quanto per la registrazione
                                    FragmentFBLogin fbFragment = new FragmentFBLogin();
                                    fTransaction = fragmentManager.beginTransaction();
                                    fTransaction.replace(R.id.frame_login, fbFragment);
                                    fTransaction.addToBackStack("fromFB");
                                    // Commit the transaction
                                    fTransaction.commit();

                                    //connectionTask.execute(jo);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, gender");
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

        try {
            JSONObject jRis = new JSONObject(output);
            Log.d("agenziaIs", jRis.toString());
            //controlli sulla risposta dal server
            //se nel JSON ricevuto non è presente il campo Status allora crea l'oggetto utente e
            //passa all'activity successiva
            if (!jRis.has("status")) {
                user = new User();
                user = user.toJava(output);
                user.saveData(jRis.getString("login"), jRis.getString("password"), getApplicationContext());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            } else {
                //in caso di errore viene comunque istanziata una nuova AsyncTask
                connectionTask = new HttpAsyncTask();
                connectionTask.response = this;
                Log.d("AGENZIAISPJDM", jRis.getString("status"));
                switch (jRis.getString("status")) {
                    case "success":
                        //in caso di status "success", la registrazione è andata a buon fine,
                        //quindi ritorna alla schermata di login per l'accesso
                        Toast.makeText(getBaseContext(), getString(R.string.register_success), Toast.LENGTH_LONG).show();
                        FragmentLogin lFragment = new FragmentLogin();
                        fTransaction = fragmentManager.beginTransaction();
                        fTransaction.replace(R.id.frame_login, lFragment);
                        fTransaction.addToBackStack("fromRegister");
                        // Commit the transaction
                        fTransaction.commit();

                    case "not found":
                        //in caso di status "not found", la mail o la password sono errate,
                        //pertanto viene visualizzato solo un messaggio di errore
                        Toast.makeText(getBaseContext(), getString(R.string.login_not_found), Toast.LENGTH_LONG).show();
                        connectionTask = new HttpAsyncTask();
                        connectionTask.response = this;
                        break;

                    case "duplicated":
                        Log.d("AgenziaIs", "" + fb);
                        //nel caso di status "duplicate", bisogna controllare che sia avvenuto in seguito
                        //ad un tentativo di Login manuale o accesso con fb
                        if (!fb) {
                            //in caso l'errore sia avvenuto in seguito a Login manuale viene segnalato l'errore
                            Toast.makeText(getBaseContext(), getString(R.string.register_duplicate), Toast.LENGTH_LONG).show();
                            connectionTask = new HttpAsyncTask();
                            connectionTask.response = this;
                        } else {
                            //se invece è stato tentato l'accesso con FB allora i dati vengono girati
                            //al metodo di login
                            Log.d("AgenziaIs", "accesso fb");
                            this.jo.put("URL", getString(R.string.login_url));
                            connectionTask.execute(this.jo);
                        }
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}


