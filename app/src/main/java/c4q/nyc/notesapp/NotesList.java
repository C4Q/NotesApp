package c4q.nyc.notesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotesList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
    }

    public void launchViewNote() {
        // launches note detail activity in view mode

    }

    public void launchEditNote() {
        // opens note detail in edit mode
    }

    public void deleteNote() {
        // deletes a note
    }

    public void archiveNote() {

    }
}
