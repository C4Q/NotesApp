package c4q.nyc.notesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import c4q.nyc.notesapp.models.INotesManager;
import c4q.nyc.notesapp.models.NotesManager;

public class NotesListActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();
    private RecyclerView recyclerView;
    INotesManager notesManager;

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
        NotesListAdapter adapter = new NotesListAdapter(notesManager);
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
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // save all notes to non-volatile storage
        notesManager.persist(this);
    }
}
