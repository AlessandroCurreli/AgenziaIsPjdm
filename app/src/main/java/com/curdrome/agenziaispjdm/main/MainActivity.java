package com.curdrome.agenziaispjdm.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.connection.AsyncResponse;
import com.curdrome.agenziaispjdm.connection.HttpAsyncTask;
import com.curdrome.agenziaispjdm.login.LoginActivity;
import com.curdrome.agenziaispjdm.model.Property;
import com.curdrome.agenziaispjdm.model.User;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements AsyncResponse {

    //oggetto per la connessione
    protected HttpAsyncTask connectionTask;

    private FragmentManager mFragmentManager;

    private String[] fragmentsName;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private User user;
    private List<Property> propertiesResult = new ArrayList<Property>();
    private Property property;

    public User getUser() {
        return user;
    }

    public List<Property> getPropertiesResult() {
        return propertiesResult;
    }

    public void setPropertiesResult(List<Property> propertiesResult) {
        this.propertiesResult = propertiesResult;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creazione oggetto user con i dati passati dall'activity precedente
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        //Bundle bundle = new Bundle(savedInstanceState);

        //messaggio di benvenuto
        Toast.makeText(getBaseContext(), "Benvenuto " + user.getFirstname() + " " + user.getLastname(), Toast.LENGTH_LONG).show();

        //creazione oggetti necessari al NavigationDrawer
        fragmentsName = getResources().getStringArray(R.array.fragments_name);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, fragmentsName));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        //instanziazione fragment per la ricerca
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        SearchFragment sFragment = new SearchFragment();
        fTransaction.add(R.id.frame_main, sFragment);
        fTransaction.commit();
        getActionBar().setTitle(getString(R.string.title_search_fragment));
    }

    public void searchConnection(JSONObject jo) {

        try {
            jo.put("URL", getString(R.string.doSearch_url));
            jo.put("user_id", user.getId());
            connectionTask = new HttpAsyncTask();
            connectionTask.response = this;
            connectionTask.execute(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addBookmarkConnection(JSONObject jo) {

        try {

            boolean aggiunto = false;
            for (Property temp : user.getProperties()) {
                if (temp.getId() == jo.getInt("id")) {
                    Toast.makeText(getBaseContext(), "Immobile gia aggiunto!", Toast.LENGTH_SHORT).show();
                    aggiunto = true;
                }
            }
            if (!aggiunto) {
                jo.put("URL", getString(R.string.addBookmark_url));
                jo.put("user_id", user.getId());
                connectionTask = new HttpAsyncTask();
                connectionTask.response = this;
                property = Property.toJava(jo.toString());
                connectionTask.execute(jo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //override del metodo per la gestione dei risultati dell'AsyncTask
    @Override
    public void taskResult(String output) {

        Log.d("AGENZIAISPJDM", output);

        if (output.contains("status")) {

            try {
                JSONObject jo = new JSONObject(output);
                switch (jo.getString("status")) {
                    case "success":
                        Toast.makeText(getBaseContext(), "Immobile aggiunto a preferiti!", Toast.LENGTH_LONG).show();
                        user.addBookmark(property);
                        break;
                    case "error":
                        Toast.makeText(getBaseContext(), "Errore,impossibile aggiungere a preferiti!", Toast.LENGTH_LONG).show();
                        break;
                    case "updated":
                        Toast.makeText(getBaseContext(), "Dati del utente aggiornati!", Toast.LENGTH_LONG).show();
                        break;
                    case "not updated":
                        Toast.makeText(getBaseContext(), "Errore, dati del utente non aggiornati!", Toast.LENGTH_LONG).show();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JSONArray ja = new JSONArray(output);
                for (int i = 0; i < ja.length(); i++) {
                    propertiesResult.add(Property.toJava(ja.getJSONObject(i).toString()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //instanziazione fragment per la ricerca
            ResultFragment rFragment = new ResultFragment();
            FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
            fTransaction.replace(R.id.frame_main, rFragment);

            fTransaction.addToBackStack("fromSearch");

            fTransaction.commit();
        }
    }

    /**
     * Scambio di fragment all'interno del frame nell'activity principale
     */
    private void selectItem(int position) {
        Fragment newFragment = null;
        CharSequence title = null;
        switch (position) {
            case 0:
                //instanziazione del fragment di ricerca con titolo
                newFragment = new SearchFragment();
                title = getString(R.string.title_search_fragment);
                break;

            case 1:
                //instanziazione del fragment di ricerca con titolo
                newFragment = new BookmarkFragment();
                title = getString(R.string.title_bookmarks_fragment);
                break;

            case 2:
                newFragment = new ProfileFragment();
                title = getString(R.string.title_profile_fragment);
                break;

            case 3:
                LoginManager.getInstance().logOut();
                user = null;
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                /*
                intent.putExtra("user", user);
                */
                startActivity(intent);
                finish();
                break;
        }
        //rimpiazzamento vecchio fragment con il nuovo

        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        fTransaction.replace(R.id.frame_main, newFragment);
        fTransaction.addToBackStack("" + position);
        // Commit the transaction
        fTransaction.commit();
        mDrawerList.setItemChecked(position, true);
        getActionBar().setTitle(title);
        mDrawerLayout.closeDrawer(mDrawerList);


    }

    public void updateUserConnection(JSONObject jsonObject) {

        Log.d("AGENZIAISPJDM", jsonObject.toString());

        try {
            jsonObject.put("URL", getString(R.string.update_url));
            connectionTask = new HttpAsyncTask();
            connectionTask.response = this;
            connectionTask.execute(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }


}