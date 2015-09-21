package com.curdrome.agenziaispjdm.research;

import android.content.Context;
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
import com.curdrome.agenziaispjdm.model.Property;
import com.curdrome.agenziaispjdm.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResearchActivity extends FragmentActivity implements AsyncResponse {

    //oggetto per la connessione
    protected HttpAsyncTask connectionTask = new HttpAsyncTask();

    private FragmentManager mFragmentManager;

    private User user;
    private List<Property> propertiesResult = new ArrayList<Property>();

    public List<Property> getPropertiesResult() {
        return propertiesResult;
    }

    public void setPropertiesResult(List<Property> propertiesResult) {
        this.propertiesResult = propertiesResult;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);
        connectionTask.response = this;

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, user.getFirstname() + " " + user.getLastname() + " logged in", duration);
        toast.show();

        //instanziazione fragment per la ricerca
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        SearchFragment sFragment = new SearchFragment();


        fTransaction.add(R.id.frame_search,sFragment);
        fTransaction.commit();
    }

    public void searchConnection(JSONObject jo) {

        try {
            jo.put("URL", getString(R.string.doSearch_url));
            jo.put("user_id", user.getId());
            //jo.put("URL", "http://ispjdmtest1-curdrome.rhcloud.com/android/login");
            //jo.put("URL", "http://10.220.158.248:8080/ispjdmtest1/android/login");
            connectionTask.execute(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //override del metodo per la gestione dei risultati dell'AsyncTask
    @Override
    public void taskResult(String output) {

        try {
            JSONArray ja = new JSONArray(output);
            for (int i = 0; i < ja.length(); i++) {
                propertiesResult.add(Property.toJava(ja.getJSONObject(i).toString()));

            }
            Log.d("AGENZIAISPJDM", propertiesResult.get(0).getDescription());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //instanziazione fragment per la ricerca
        ResultFragment rFragment = new ResultFragment();
        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        fTransaction.replace(R.id.frame_search, rFragment);

        fTransaction.addToBackStack("fromSearch");

        fTransaction.commit();

    }
}
