package com.curdrome.agenziaispjdm.main;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.curdrome.agenziaispjdm.R;
import com.curdrome.agenziaispjdm.adapters.BookmarkAdapter;
import com.curdrome.agenziaispjdm.model.Property;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends android.support.v4.app.Fragment {

    private MainActivity activity;
    private BookmarkFragment adapter;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    //salvataggio fragment in Bundle
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("bookmarks", this.adapter);
        super.onSaveInstanceState(outState);
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
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Property> listBookmarks = activity.getUser().getProperties();


        final ListView mylist = (ListView) view.findViewById(R.id.bookmarks);
        //final BookmarkAdapter
        adapter = new BookmarkAdapter(getActivity().getBaseContext(),
                R.layout.bookmark_row, listBookmarks, activity);
        mylist.setAdapter(adapter);

    }
}
