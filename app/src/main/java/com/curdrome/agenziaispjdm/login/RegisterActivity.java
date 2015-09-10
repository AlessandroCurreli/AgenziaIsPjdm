package com.curdrome.agenziaispjdm.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.connection.AsyncResponse;
import com.curdrome.agenziaispjdm.connection.HttpAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends Activity implements AsyncResponse {

    //oggetto per la connessione al server
    protected HttpAsyncTask connectionTask = new HttpAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        connectionTask.response = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView tvWarnings = (TextView) findViewById(R.id.tv_warnings);

        //creazione oggetti per i form da compilare
        final EditText emailText = (EditText) findViewById(R.id.email_text);
        final EditText nameText = (EditText) findViewById(R.id.name_text);
        final EditText surnameText = (EditText) findViewById(R.id.surname_text);
        final EditText phoneText = (EditText) findViewById(R.id.phone_text);
        final EditText passwordText = (EditText) findViewById(R.id.password_text);

        //creazione bottone di registrazione
        Button btRegister = (Button) findViewById(R.id.register_button);

        //gestione evento bottone premuto
        btRegister.setOnClickListener(new View.OnClickListener() {
            //ad evento avvenuto, vengono raccolti i dati ed
            //inviati al server per la registrazione
            @Override
            public void onClick(View v) {
                String email, name, surname,password;
                Double phone;
                email = emailText.getText().toString();
                name = nameText.getText().toString();
                surname = surnameText.getText().toString();
                phone = Double.parseDouble(phoneText.getText().toString());
                password = passwordText.getText().toString();

                registerConnection(email, name, surname, phone, password);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //metodo che organizza i dati in un JSON e lo invia al server
    public void registerConnection(String email, String firstname, String lastname, Double phone, String password){
        JSONObject jo = new JSONObject();

        try {
            jo.put("login",email);
            jo.put("password",password);
            jo.put("firstname",firstname);
            jo.put("lastname",lastname);
            jo.put("phone",phone);
            jo.put("email",email);
            jo.put("URL", "http://ispjdmtest1-curdrome.rhcloud.com/android/register");
            connectionTask.execute(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //ovverride del metodo per la gestione del risultato della regstrazione
    //se è andata a buon fine si passa all'activity di Login
    //altrimenti viene restituito errore
    @Override
    public void taskResult(String output) {
        JSONObject jo = null;
        try {
            jo = new JSONObject(output);
            if (jo.getString("status").equals("success")){
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
