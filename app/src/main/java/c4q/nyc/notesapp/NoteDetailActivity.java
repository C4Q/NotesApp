package c4q.nyc.notesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class NoteDetailActivity extends AppCompatActivity {

    public static final String NOTE_TITLE = "note_title";
    public static final String NOTE_BODY = "note_body";
    public static final String NOTE_ID = "note_id";
    private String noteId;
    private EditText editTitle;
    private EditText editBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        editTitle = (EditText) findViewById(R.id.edit_title);
        editBody = (EditText) findViewById(R.id.edit_body);

        if (getIntent().hasExtra(NOTE_TITLE)) {
            editTitle.setText(getIntent().getStringExtra(NOTE_TITLE));
        }
        if (getIntent().hasExtra(NOTE_BODY)) {
            editBody.setText(getIntent().getStringExtra(NOTE_BODY));
        }
        if (getIntent().hasExtra(NOTE_ID)) {
            noteId = getIntent().getStringExtra(NOTE_ID);
        }

    }

    @Override
    public void onBackPressed() {
        saveAndExit(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveAndExit(null);
    }

    // Save button click handler
    public void saveAndExit(View view) {
        Intent data = new Intent();
        data.putExtra(NOTE_ID, noteId);
        data.putExtra(NOTE_TITLE, editTitle.getText().toString());
        data.putExtra(NOTE_BODY, editBody.getText().toString());

        if (getParent() == null) {
            setResult(Activity.RESULT_OK, data);
        } else {
            getParent().setResult(Activity.RESULT_OK, data);
        }

        finish();
    }
}
