package com.curdrome.agenziaispjdm.main;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.adapters.ResultsAdapter;
import com.curdrome.agenziaispjdm.connection.AsyncResponse;
import com.curdrome.agenziaispjdm.connection.HttpAsyncTask;
import com.curdrome.agenziaispjdm.adapters.BookmarkAdapter;
import com.curdrome.agenziaispjdm.model.Property;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends android.support.v4.app.Fragment implements AsyncResponse {

    private HttpAsyncTask connectionTask;
    private Property propertyRemoved;
    private MainActivity activity;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Property> listBookmarks = activity.getUser().getProperties();


        final ListView mylist = (ListView) view.findViewById(R.id.results);
        final BookmarkAdapter adapter = new BookmarkAdapter(getActivity().getBaseContext(),
                R.layout.result_row, listBookmarks, activity);
        mylist.setAdapter(adapter);

    }

    public void removeBookmark(Property property) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("id", property.getId());
            jo.put("user_id", activity.getUser().getId());
            jo.put("URL", getString(R.string.removeBookmark_url));
            propertyRemoved = property;
            connectionTask = new HttpAsyncTask();
            connectionTask.response = this;
            connectionTask.execute(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void taskResult(String output) {
        try {
            JSONObject jRis = new JSONObject(output);
            switch (jRis.getString("status")) {
                case "success":
                    activity.getUser().removeBookmark(propertyRemoved);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
