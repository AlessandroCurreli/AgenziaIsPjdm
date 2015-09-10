package com.curdrome.agenziaispjdm.research;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.curdrome.agenziaispjdm.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    ArrayList<String> selection=new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View viewSelection =inflater.inflate(R.layout.activity_search_main2, container, false);

        return viewSelection;
    }

    public void select_item(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.Immobili_1:
                if (checked) {
                    selection.add("Appartamento");
                } else {
                    selection.remove("Appartamento");
                }
                break;
            case R.id.Immobili_2:
                if (checked) {
                    selection.add("Villa");
                } else {
                    selection.remove("Villa");
                }
                break;
            case R.id.Immobili_3:
                if (checked) {
                    selection.add("Monolocale");
                } else {
                    selection.remove("Monolocale");
                }
                break;
            case R.id.Immobili_4:
                if (checked) {
                    selection.add("Ufficio");
                } else {
                    selection.remove("Ufficio");
                }
                break;
            case R.id.Immobili_5:
                if (checked) {
                    selection.add("Negozio");
                } else {
                    selection.remove("Negozio");
                }
                break;
            case R.id.Immobili_6:
                if (checked) {
                    selection.add("Magazzino");
                } else {
                    selection.remove("Magazzino");
                }
                break;
            case R.id.Immobili_7:
                if (checked) {
                    selection.add("Box");
                } else {
                    selection.remove("Box");
                }
                break;
            case R.id.Immobili_8:
                if (checked) {
                    selection.add("Terreno");
                } else {
                    selection.remove("Terreno");
                }
                break;
            case R.id.idprezzo1:
                if (checked) {
                    selection.add("Fino a 100.000");
                } else {
                    selection.remove("Fino a 100.000");
                }
                break;
            case R.id.idPrezzo2:
                if (checked) {
                    selection.add("Da 100.000 a 300.000");
                } else {
                    selection.remove("Da 100.000 a 300.000");
                }
                break;
            case R.id.idPrezzo3:
                if (checked) {
                    selection.add("Oltre 300.000");
                } else {
                    selection.remove("Oltre 300.000");
                }
                break;
            case R.id.idMq1:
                if (checked) {
                    selection.add("Fino a 50mq");
                } else {
                    selection.remove("Fino a 50mq");
                }
                break;
            case R.id.idMq2:
                if (checked) {
                    selection.add("Da 50 a 100 mq");
                } else {
                    selection.remove("Da 50 a 100mq");
                }
                break;
            case R.id.idMq3:
                if (checked) {
                    selection.add("Da 100 a 200 mq");
                } else {
                    selection.remove("Da 100 a 200 mq");
                }
                break;
            case R.id.idMq4:
                if (checked) {
                    selection.add("Oltre 200 mq");
                } else {
                    selection.remove("Oltre 200 mq");
                }
                break;

        }

    }
}
