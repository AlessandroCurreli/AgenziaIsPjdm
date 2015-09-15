package com.curdrome.agenziaispjdm.research;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import android.widget.Toast;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.connection.AsyncResponse;
import com.curdrome.agenziaispjdm.connection.HttpAsyncTask;
import com.curdrome.agenziaispjdm.model.User;

public class ResearchActivity extends FragmentActivity implements AsyncResponse {

    //oggetto per la connessione
    protected HttpAsyncTask connectionTask = new HttpAsyncTask();

    private FragmentManager mFragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);
        connectionTask.response = this;

        Intent intent = getIntent();
        User user = (User)intent.getSerializableExtra("user");

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, user.getFirstname()+" "+user.getLastname()+"logged in", duration);
        toast.show();

        //instanziazione fragment per la ricerca
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        SearchFragment sFragment = new SearchFragment();


        fTransaction.add(R.id.frame_search,sFragment);
        fTransaction.commit();
    }

    @Override
    public void taskResult(String output) {

        //TODO gestione risultati al result fragment rimpiazzando il fragment search (da vedere la gestione di backstack)

    }
}
