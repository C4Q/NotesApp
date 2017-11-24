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

import java.util.ArrayList;

import c4q.nyc.notesapp.models.INotesManager;
import c4q.nyc.notesapp.models.Note;
import c4q.nyc.notesapp.models.NotesManager;

public class NotesListActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();
    private RecyclerView recyclerView;
    public static final int NEW_NOTE_REQUEST_CODE = 888;
    INotesManager notesManager;
    NotesListAdapter adapter;
    ArrayList<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        try {
            notesManager = NotesManager.FromFile(this);
        } catch (Exception e) {
            Log.d(TAG, "Failed to initialize notes manager from file. Using default constructor");
            notesManager = new NotesManager();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        notesList = new ArrayList<>(notesManager.getNotes());
        adapter = new NotesListAdapter(notesList);
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
            notesList.add(notesManager.createNote(title, body));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // save all notes to non-volatile storage
        notesManager.persist(this);
    }
}
