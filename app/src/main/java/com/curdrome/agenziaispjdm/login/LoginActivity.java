package com.curdrome.agenziaispjdm.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.connection.AsyncResponse;
import com.curdrome.agenziaispjdm.connection.HttpAsyncTask;
import com.curdrome.agenziaispjdm.model.User;
import com.curdrome.agenziaispjdm.research.ResearchActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends FragmentActivity implements AsyncResponse {

    protected User user = new User();
    protected HttpAsyncTask connectionTask = new HttpAsyncTask();
    private TextView warnings;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connectionTask.response = this;
        //creazione varaibili legate ai campi del form
        warnings = (TextView)findViewById(R.id.tv_warnings);

        //instanziazione fragment con il bottone per il login con i dati di Facebook
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        FragmentLoginFB fbFragment = new FragmentLoginFB();
        FragmentLogin lFragment = new FragmentLogin();

        fTransaction.add(R.id.frame_fb, fbFragment);
        fTransaction.add(R.id.frame_login, lFragment);
        fTransaction.commit();

        //Registrazione
        Button btRegister = (Button) findViewById(R.id.register_button);

        btRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void loginConnection(String email, String password){

        JSONObject jo = new JSONObject();
        //TODO aggiunta isConnected()
        try {
            jo.put("login",email);
            jo.put("password",password);
            jo.put("URL", getString(R.string.login_url));
            connectionTask.execute(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //override del metodo per la gestione dei risultati dell'AsyncTask
    @Override
    public void taskResult(String output) {

        //TODO controllo sui risultati e in caso negativo istanzio nuova AsyncTask
        //converto il risultato ad oggetto user
        User user = new User();
        user = user.toJava(output);
        Intent intent = new Intent(LoginActivity.this, ResearchActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();

    }

    //stampa un messaggio di errore nel caso username e/o password siano errati
    public void printError(){
        warnings.setText("Username o password errate");

    }

    //TODO controllo dati in locale per l'invio in automatico di Login e Password
    public boolean alreadyExisted(){

        return true;
    }

}
