package com.curdrome.agenziaispjdm.main;

import android.content.Context;
import android.content.ContextWrapper;
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
import com.curdrome.agenziaispjdm.login.FragmentLogin;
import com.curdrome.agenziaispjdm.login.LoginActivity;
import com.curdrome.agenziaispjdm.model.Property;
import com.curdrome.agenziaispjdm.model.User;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    private Fragment fragment;
    private JSONObject temp = new JSONObject();

    public User getUser() {
        return user;
    }

    public List<Property> getPropertiesResult() {
        return propertiesResult;
    }

    public void setPropertiesResult(List<Property> propertiesResult) {
        this.propertiesResult = propertiesResult;
    }

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
        setContentView(R.layout.activity_main);
        String title = null;

        //creazione oggetto user con i dati passati dall'activity precedente
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        //Bundle bundle = new Bundle(savedInstanceState);

        //messaggio di benvenuto
        if (fragment == null)
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
        if (fragment != null) {
            fTransaction.replace(R.id.frame_main, fragment);
        } else {
            fragment = new SearchFragment();
            fTransaction.add(R.id.frame_main, fragment);
            title = getString(R.string.title_search_fragment);

        }
        fTransaction.commit();
        getActionBar().setTitle(title);
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

    //override del metodo per la gestione dei risultati dell'AsyncTask
    @Override
    public void taskResult(String output) {

        Log.d("AGENZIAISPJDM", output);

        if (output.contains("status")) {

            try {
                JSONObject jo = new JSONObject(output);
                switch (jo.getString("status")) {

                    case "removed":
                        Toast.makeText(getBaseContext(), "Immobile rimosso da preferiti!", Toast.LENGTH_LONG).show();
                        user.removeBookmark(property);

                        BookmarkFragment bFragment = new BookmarkFragment();
                        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
                        fTransaction.replace(R.id.frame_main, bFragment);

                        fTransaction.addToBackStack("fromBookmark");

                        fTransaction.commit();
                        break;
                    case "not removed":
                        Toast.makeText(getBaseContext(), "Immobile non rimosso da preferiti!", Toast.LENGTH_LONG).show();
                        break;
                    case "success":
                        Toast.makeText(getBaseContext(), "Immobile aggiunto a preferiti!", Toast.LENGTH_LONG).show();
                        user.addBookmark(property);
                        break;
                    case "error":
                        Toast.makeText(getBaseContext(), "Errore,impossibile aggiungere a preferiti!", Toast.LENGTH_LONG).show();
                        break;
                    case "updated":
                        user.saveData(temp.getString("login"), temp.getString("new_password"), getApplicationContext());
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

            propertiesResult.removeAll(propertiesResult);

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


    public void updateUserConnection(JSONObject jsonObject) {

        Log.d("AGENZIAISPJDM", jsonObject.toString());

        try {
            jsonObject.put("URL", getString(R.string.update_url));
            connectionTask = new HttpAsyncTask();
            connectionTask.response = this;
            connectionTask.execute(jsonObject);
            temp.put("login", jsonObject.getString("login"));
            if (jsonObject.has("new_password"))
                temp.put("new_password", jsonObject.getString("new_password"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeBookmarkConnection(JSONObject jo) {

        try {

            boolean victim = false;
            for (Property temp : user.getProperties()) {
                if (temp.getId() == jo.getInt("id"))
                    victim = true;
            }
            if (victim) {
                jo.put("URL", getString(R.string.removeBookmark_url));
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
                Context context = getApplicationContext();
                ContextWrapper contextWrapper = new ContextWrapper(context);
                File directory = contextWrapper.getDir(context.getString(R.string.file_dir), Context.MODE_PRIVATE);
                File file = new File(directory, context.getString(R.string.file_name));
                file.delete();
                user = null;
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
        }
        //rimpiazzamento vecchio fragment con il nuovo

        FragmentTransaction fTransaction = mFragmentManager.beginTransaction();
        fTransaction.replace(R.id.frame_main, newFragment);
        fTransaction.addToBackStack("" + position);
        // Commit the transaction
        fTransaction.commit();
        mDrawerList.setItemChecked(position, true);
        try {
            getActionBar().setTitle(title);
        } catch (NullPointerException npex) {
            Log.d("ERROR", "non è stato possibile egeguire il setTitle() sull'actionBar");
        }
        mDrawerLayout.closeDrawer(mDrawerList);


    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }


}