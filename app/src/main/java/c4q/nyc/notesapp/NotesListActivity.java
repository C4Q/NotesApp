package c4q.nyc.notesapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;

import c4q.nyc.notesapp.models.IDataSource;
import c4q.nyc.notesapp.models.SharedPrefDataSource;

public class NotesListActivity extends AppCompatActivity {

    private static final int EDIT_NOTE_REQUEST_CODE = 999;
    private static final int NEW_NOTE_REQUEST_CODE = 888;
    protected static IDataSource DataSource;
    private final String TAG = getClass().getName();
    private RecyclerView recyclerView;
    private NotesListAdapter adapter;
    private LinkedList<View> selectedViews = new LinkedList<>();
    private ActionMode multiSelectMode;

    /**
     * Handles onClick event for an item (ViewHolder) in the recycler view.
     * - When the list is in contextual action mode (multi-selecting), clicking just selects the item
     * - otherwise, clicking would build a new intent and open the NoteDetailActivity
     * WARNING: this is a hack - it works for trivial amount of data and test cases, but this is RecyclerView!
     * - The selected view is not guaranteed to hold the same data after you've scrolled past it
     * - And I should be keeping a list of selected ids, not views. But like I said, it's just a hack... that you can improve
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (multiSelectMode != null) {
                v.findViewById(R.id.note_item_wrapper).setBackgroundColor(getResources().getColor(R.color.light_blue));
                selectedViews.add(v);
                return;
            }

            TextView title = v.findViewById(R.id.note_item_title);
            String id = (String) title.getTag();
            TextView body = v.findViewById(R.id.note_item_body);

            Intent i = new Intent(NotesListActivity.this, NoteDetailActivity.class);
            i.putExtra(NoteDetailActivity.NOTE_ID, id);
            i.putExtra(NoteDetailActivity.NOTE_TITLE, title.getText().toString());
            i.putExtra(NoteDetailActivity.NOTE_BODY, body.getText().toString());
            startActivityForResult(i, EDIT_NOTE_REQUEST_CODE);
        }
    };
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.noteslist_contextual_action_mode_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            for (View v : selectedViews) {
                v.findViewById(R.id.note_item_wrapper).setBackgroundColor(getResources().getColor(R.color.white));
            }
            multiSelectMode = null;
        }
    };

    /**
     * Handles long-pressing on a note item (ViewHolder) in the recycler view
     * - it basically enables multi-select mode
     */
    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            // action mode already enabled, do nothing / translate to click
            if (multiSelectMode != null) {
                return false;
            }

            multiSelectMode = startActionMode(mActionModeCallback);
            v.setSelected(true);
            // normally we should return true, but we're returning false here so this item gets "clicked" to update background color
            return false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        Context context = this;
        DataSource = SharedPrefDataSource.getInstance(context);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new NotesListAdapter(DataSource, onClickListener, onLongClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    protected void onCreateAsync(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        AsyncTask<Context, Void, Void> load = new AsyncTask<Context, Void, Void>() {

            Context context;

            @Override
            protected Void doInBackground(Context... contexts) {
                context = contexts[0];
                DataSource = SharedPrefDataSource.getInstance(context);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                adapter = new NotesListAdapter(DataSource, onClickListener, onLongClickListener);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
        };

        load.execute(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.noteslist_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu_item:
                Log.d(TAG, "clicked settings menu");
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return true;
    }

    public void startActivityForNewNote(View v) {
        Intent intent = new Intent(this, NoteDetailActivity.class);
        startActivityForResult(intent, NEW_NOTE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK &&
                (requestCode == NEW_NOTE_REQUEST_CODE || requestCode == EDIT_NOTE_REQUEST_CODE)) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // save all notes to non-volatile storage
        DataSource.persist(this, DataSource);
    }
}
