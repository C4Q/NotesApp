package c4q.nyc.notesapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import c4q.nyc.notesapp.models.FileDataSource;
import c4q.nyc.notesapp.models.IDataSource;

public class NoteDetailActivity extends AppCompatActivity {

    public static final String NOTE_TITLE = "note_title";
    public static final String NOTE_BODY = "note_body";
    public static final String NOTE_ID = "note_id";
    private static IDataSource dataSource;
    private String noteId;
    private EditText editTitle;
    private EditText editBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        dataSource = FileDataSource.getInstance(this);

        editTitle = (EditText) findViewById(R.id.edit_title);
        editBody = (EditText) findViewById(R.id.edit_body);

        editTitle.setText(getIntent().getStringExtra(NOTE_TITLE));
        editBody.setText(getIntent().getStringExtra(NOTE_BODY));
        noteId = getIntent().getStringExtra(NOTE_ID);
    }

    @Override
    public void onBackPressed() {
        saveAndExit(null);
    }

    // Save button click handler
    public void saveAndExit(View view) {
        String title = editTitle.getText().toString();
        String body = editBody.getText().toString();

        if (noteId != null && !noteId.isEmpty()) {
            dataSource.updateNote(noteId, title, body);
        } else {
            dataSource.addNote(title, body);
        }

        if (getParent() == null) {
            setResult(Activity.RESULT_OK);
        } else {
            getParent().setResult(Activity.RESULT_OK);
        }
        finish();
    }
}
