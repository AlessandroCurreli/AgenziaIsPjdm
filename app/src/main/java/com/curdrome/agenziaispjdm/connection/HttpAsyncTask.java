package com.curdrome.agenziaispjdm.connection;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

/**
 * Created by Alex on 03/09/2015.
 */
public class HttpAsyncTask extends AsyncTask<JSONObject, Void, String> {
    public AsyncResponse response=null;

    //User user = new User();

    @Override
    protected String doInBackground(JSONObject... jo) {
        String url= null;
        try {
            //user.setLogin(jo[0].getString("Login"));
            //user.setPassword(jo[0].getString("Password"));
            url = jo[0].getString("URL");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return connectionPOST(url,jo[0]);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

        //Toast.makeText(getBaseContext(), "Oggetto user "+ user.getLogin() +" "+user.getPassword() +" ", Toast.LENGTH_LONG).show();
        //Toast.makeText(getBaseContext(), "User from form "+ result +" ", Toast.LENGTH_LONG).show();

        /*JSONObject jo = null;
        try {
            jo = new JSONObject(result);
            String password = jo.getString("password");
            if (user.getPassword().equals(password))
                Toast.makeText(getBaseContext(), "Welcome " + user.getLogin() + "! ", Toast.LENGTH_LONG).show();
            else Toast.makeText(getBaseContext(), "errore! ", Toast.LENGTH_LONG).show();

            //if (user.getPassword().equals(password)){
            //lancio activity principale
            //}

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        response.taskResult(result);
    }

    //Metodo che utilizza i dati inseriti o prelevati dall'account Facebook per connettersi al
    //proprio account
    public String connectionPOST(String url, JSONObject jo){
        //String result = " "+user.getLogin()+" " +user.getPassword()+" " +url+" ";

        InputStream inputStream = null;
        String result = "";
        try {
            //creazione oggetto HttpClient
            HttpClient httpClient = new DefaultHttpClient();
            //creazione oggetto request Post per l'URL dato
            HttpPost httpPost = new HttpPost(url);

            //creazione oggetto JSON per i dati
            String jstring="";
            //JSONObject jo = new JSONObject();
            //aggiunta dei dati
            //jo.accumulate("login", user.getLogin());
            //jo.accumulate("password",user.getPassword());
            jstring=jo.toString();
            //settaggio "entità" per il passaggio dati
            StringEntity se = new StringEntity(jstring);

            httpPost.setEntity(se);
            //settaggio Header per l'invio
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            //invio dati al server
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //risposta dal server inserita in un buffer
            inputStream = httpResponse.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = br.readLine()) != null) {
                result += line;
            }
            inputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (java.net.ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
