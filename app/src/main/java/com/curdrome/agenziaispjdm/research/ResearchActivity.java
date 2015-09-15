package com.curdrome.agenziaispjdm.research;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.connection.AsyncResponse;
import com.curdrome.agenziaispjdm.connection.HttpAsyncTask;

import java.util.ArrayList;

public class ResearchActivity extends FragmentActivity implements AsyncResponse {

    //oggetto per la connessione
    protected HttpAsyncTask connectionTask = new HttpAsyncTask();

    private SearchFragment.Residenziale residenziale=new SearchFragment.Residenziale();
    private SearchFragment.Commerciale commerciale=new SearchFragment.Commerciale();
    private ArrayAdapter<String>=listviewAdapter;
    private ArrayAdapter<String>=spinner1;
    private ArrayAdapter<String>=spinner2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);
        connectionTask.response = this;

        // preparazione della ListView per l'elenco delle tipologie resdenziale
        ListView lv1=(ListView) findViewById(R.id.residenziale);
        listviewAdapter=new ArrayAdapter<String>(this, R.layout.row);
        lv1.setAdapter(listviewAdapter);

        // preparazione della ListView per l'elenco delle tipologie commerciale
        ListView lv2=(ListView) findViewById(R.id.commerciale);
        listviewAdapter=new ArraAdapter<String>(this, R.layout.row);
        lv2.setAdapter(listviewAdapter);

        // preparazione dello Spinner per mostrare l'elenco delle Tipologie
        spinnerAdapter=new ArrayAdapter<String>(this, R.layout.row);
        spinnerAdapter.addAll(residenziale.getResidenziale());
        Spinner sp1=(Spinner) findViewById(R.id.residenziale);
        sp1.setAdapter(spinnerAdapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                TextView txt=(TextView) arg1.findViewById(R.id.rowtext);
                String s=txt.getText().toString();
                updateResidenziale(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            { }
        });
    }

    private void updateResidenziale(String residenza)
    {
        ArrayList<String> l=(ArrayList<String>)
                residenziale.getResidenziale(residenza);
        listviewAdapter.clear();
        listviewAdapter.addAll(l);
    }

    spinnerAdapter.addAll(commerciale.getCommerciale());
    Spinner sp2=(Spinner) findViewById(R.id.commerciale);
    sp2.setAdapter(spinnerAdapter);
    sp2.setOnItemSelectedListener(new OnItemSelectedListener()
    {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1,
        int arg2, long arg3) {
        TextView txt=(TextView) arg1.findViewById(R.id.rowtext);
        String s=txt.getText().toString();
        updateResidenziale(s);
    }

        @Override
        public void onNothingSelected(AdapterView<?> arg0)
        { }
    });
}

    private void updateCommerciale(String com)
    {
        ArrayList<String> l=(ArrayList<String>)
                commerciale.getCommerciale(com);
        listviewAdapter.clear();
        listviewAdapter.addAll(l);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_research, menu);
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

    //scrivere metodo per la connessione (organizza i dati in JSONObject con aggiunta di URL)

    //metodo che gestisce i risultati
    @Override
    public void taskResult(String output) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        TextView myText=(TextView) view;
        Toast.makeText(this, "You selected" + myText.getText(), Toast.LENGTH_SHORT).show();

    }
}
