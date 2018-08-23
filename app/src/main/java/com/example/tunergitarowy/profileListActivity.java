package com.example.tunergitarowy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tunergitarowy.dummy.DummyContent;

import java.util.List;

/**
 * An activity representing a list of profiles. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link profileDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class profileListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO:: Dodac profil do listy w recyclerView (setAdapter)
                // Na razie nie ma profili :(
                Snackbar.make(view, "Utworzono nowy profil", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //mValues.add();
            }
        });

        if (findViewById(R.id.profile_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            //mTwoPane = true;
        }
        mTwoPane = false;

        View recyclerView = findViewById(R.id.profile_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //TODO: podmienic DummyContent na liste profili w TunerApp
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, ((TunerApp) this.getApplication()).getProfiles(), mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final profileListActivity mParentActivity;
        private final List<Profile> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: przeportowac to na class Profiles z TunerApp
                Profile item = (Profile) view.getTag();
                //TODO: Olac TwoPane?
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(profileDetailFragment.ARG_ITEM_ID, item.getName());
                    profileDetailFragment fragment = new profileDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.profile_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, profileDetailActivity.class);
                    intent.putExtra(profileDetailFragment.ARG_ITEM_ID, item.getName());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(profileListActivity parent,
                                      List<Profile> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.profile_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(new String(String.valueOf(mValues.get(position).getId())));
            holder.mContentView.setText(mValues.get(position).getName());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            // TODO: To jak lista wyglada? Nie potrzebujemy w sumie iD (No i nie przechowujemy)
            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
