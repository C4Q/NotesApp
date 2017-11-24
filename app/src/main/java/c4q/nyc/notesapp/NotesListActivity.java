package c4q.nyc.notesapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import c4q.nyc.notesapp.models.DataSource;
import c4q.nyc.notesapp.models.IDataSource;
import c4q.nyc.notesapp.models.Note;

public class NotesListActivity extends AppCompatActivity {

    private static final int EDIT_NOTE_REQUEST_CODE = 999;
    private static final int NEW_NOTE_REQUEST_CODE = 888;
    private final String TAG = getClass().getName();
    private RecyclerView recyclerView;
    private IDataSource dataSource;
    private NotesListAdapter adapter;
    private ArrayList<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        dataSource = new DataSource();
        try {
            notesList = dataSource.getData(this);
        } catch (Exception e) {
            Log.d(TAG, "Failed to initialize notes list from file. Using default constructor");
            notesList = new ArrayList<>();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new NotesListAdapter(notesList, recyclerOnClickListener);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.noteslist_menu, menu);
      return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.add_note_button:
                Intent intent = new Intent(this, NoteDetailActivity.class);
                startActivityForResult(intent, NEW_NOTE_REQUEST_CODE);
                break;
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_NOTE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String title = data.getStringExtra(NoteDetailActivity.NOTE_TITLE);
            String body = data.getStringExtra(NoteDetailActivity.NOTE_BODY);
            notesList.add(dataSource.createNote(title, body));
            adapter.notifyDataSetChanged();
        }

        if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String id = data.getStringExtra(NoteDetailActivity.NOTE_ID);
            String title = data.getStringExtra(NoteDetailActivity.NOTE_TITLE);
            String body = data.getStringExtra(NoteDetailActivity.NOTE_BODY);
            // find the note and update it
            for(int i = 0; i < notesList.size(); i++) {
                Note n = notesList.get(i);
                if(n.id.equals(id)) {
                    n.title = title;
                    n.body = body;
                    n.lastModified++;
                    break;
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // save all notes to non-volatile storage
        dataSource.persist(this, notesList);
    }

    View.OnClickListener recyclerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView title =  v.findViewById(R.id.note_item_title);
            String id = (String) title.getTag();
            TextView body = v.findViewById(R.id.note_item_body);

            Intent i = new Intent(NotesListActivity.this, NoteDetailActivity.class);
            i.putExtra(NoteDetailActivity.NOTE_ID, id);
            i.putExtra(NoteDetailActivity.NOTE_TITLE, title.getText().toString());
            i.putExtra(NoteDetailActivity.NOTE_BODY, body.getText().toString());
            startActivityForResult(i, EDIT_NOTE_REQUEST_CODE);
        }
    };
}
