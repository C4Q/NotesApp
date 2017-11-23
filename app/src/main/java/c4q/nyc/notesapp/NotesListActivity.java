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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        INotesManager notesManager;
        try {
            notesManager = NotesManager.FromFile("notes.xml");
        } catch (Exception e) {
            Log.i(TAG, "Failed to initialize notes manager from file");
            Log.d(TAG, e.toString());
            notesManager = new NotesManager();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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
}
