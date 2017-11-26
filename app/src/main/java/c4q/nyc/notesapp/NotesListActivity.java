package c4q.nyc.notesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import c4q.nyc.notesapp.models.DataSource;
import c4q.nyc.notesapp.models.IDataSource;

public class NotesListActivity extends AppCompatActivity {

    private static final int EDIT_NOTE_REQUEST_CODE = 999;
    private static final int NEW_NOTE_REQUEST_CODE = 888;
    private final String TAG = getClass().getName();
    private RecyclerView recyclerView;
    private IDataSource dataSource;
    private NotesListAdapter adapter;
    private View.OnClickListener recyclerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        dataSource = DataSource.getInstance(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new NotesListAdapter(dataSource, recyclerOnClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.noteslist_menu, menu);
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
        dataSource.persist(this, dataSource);
    }
}
