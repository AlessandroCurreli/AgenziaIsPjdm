package com.curdrome.agenziaispjdm.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.connection.AsyncResponse;
import com.curdrome.agenziaispjdm.connection.HttpAsyncTask;
import com.curdrome.agenziaispjdm.model.User;

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
        user.setLogin(email);
        user.setPassword(password);

        JSONObject jo = new JSONObject();
        try {
            jo.put("login",email);
            jo.put("password",password);
            jo.put("URL", "http://ispjdmtest1-curdrome.rhcloud.com/android/login");
            //jo.put("URL", "http://10.220.158.248:8080/ispjdmtest1/android/login");
            connectionTask.execute(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    //override del metodo per la gestione dei risultati dell'AsyncTask
    @Override
    public void taskResult(String output) {
        Toast.makeText(getBaseContext(), "Welcome " + user.getLogin() + "! ", Toast.LENGTH_LONG).show();
        //Toast.makeText(getBaseContext(), "Welcome "+user.getLogin()+"! ", Toast.LENGTH_LONG).show();

        /*try {
            JSONObject result = new JSONObject(output);

            //verifica risultati:
            //se viene ricevuto uno status di errore, significa che l'utente cercato non è presente nel
            //database, pertanto viene restituito errore
            if (result.getString("status").equals("error")){
                printError();
            }
            //se username e password inseriti corrispondono a quelli nel DB allora viene avviata
            //la prossima activity altrimenti restituisce errore
            if (result.getString("login").equals(user.getLogin()) &&
                    result.getString("password").equals(user.getPassword())){
                nextActivity();
            }//else printError();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

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
