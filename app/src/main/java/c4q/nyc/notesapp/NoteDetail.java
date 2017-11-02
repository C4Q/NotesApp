package c4q.nyc.notesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NoteDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        // should determine it's state (view or edit) on start by checking extras
    }

    public void editNote() {
        // make the current note editable if not already editable
    }

    public void saveNote() {
        // save the current not and return to previous view
    }
}
