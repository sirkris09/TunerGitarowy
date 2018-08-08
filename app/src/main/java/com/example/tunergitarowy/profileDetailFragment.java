package com.example.tunergitarowy;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tunergitarowy.dummy.DummyContent;

import java.util.ArrayList;
import java.util.Arrays;

// TODO: a wlasciwie nie-TODO, ta czesc dziala tylko na tabletach (szeroki ekran) mozna olac?
/**
 * A fragment representing a single profile detail screen.
 * This fragment is either contained in a {@link profileListActivity}
 * in two-pane mode (on tablets) or a {@link profileDetailActivity}
 * on handsets.
 */
public class profileDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Profile mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public profileDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            Log.i("profileetailFragment: ", getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            mItem = ((TunerApp) activity.getApplication()).findProfile(getArguments().getString(ARG_ITEM_ID));
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_detail_spinners, container, false);
        if(mItem != null) {
            final Spinner stringSpinner = (Spinner) rootView.findViewById(R.id.stringSpinner);
            final Spinner noteSpinner = (Spinner) rootView.findViewById(R.id.noteSpinner);
            final Spinner octaveSpinner = (Spinner) rootView.findViewById(R.id.octaveSpinner);

            final ArrayAdapter<String> stringArrayAdapter;

            ArrayList<Integer> strings = this.mItem.getTones();

            ArrayList<String> lst1 = new ArrayList<String>();
            for (Integer i : strings) {
                lst1.add(String.valueOf(i));
            }
            stringArrayAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, lst1);

            stringSpinner.setAdapter(stringArrayAdapter);

        }

        // Show the dummy content as text in a TextView.

        return rootView;
    }
}
