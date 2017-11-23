package c4q.nyc.notesapp.models;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import c4q.nyc.notesapp.R;

/**
 * Implements INotesManager using HashMap as underlying storage
 */

public class NotesManager implements INotesManager {
    private static final String NOTES_FILE = "notes.json";
    private static final String TAG = "NotesManager";
    private HashMap<String, Note> notes = new HashMap<>();

    /**
     * De-serializes a Notes json file into an INotesManager
     * @param context
     * @return
     */
    public static INotesManager FromFile(Context context) throws FileNotFoundException {
        Type collectionType = new TypeToken<Collection<Note>>() {}.getType();
        Gson gs = new Gson();
        Collection<Note> notes = null;

        // try to load the data file (where we save to)
        FileInputStream fis = context.openFileInput(NOTES_FILE);
        notes = gs.fromJson(new InputStreamReader(fis), collectionType);

        // above failed, use initializer file
        if (notes == null) {
            Log.d(TAG, "Using initializer file resource");
            InputStream is = context.getResources().openRawResource(R.raw.notes);
            InputStreamReader isr = new InputStreamReader(is);
            notes = gs.fromJson(isr, collectionType);
        }

        // above two failed, use empty list
        if(notes == null) {
            Log.d(TAG, "No data was loaded from any storage, initializing empty notes manager");
            notes = new ArrayList<>();
        }

        NotesManager notesManager = new NotesManager();
        for(Note n: notes) {
            notesManager.notes.put(n.id, n);
        }
        return notesManager;
    }

    /**
     * Serializes a notes to a json file
     * @Param context
     */
    public void persist(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(NOTES_FILE, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            new Gson().toJson(getNotes(), writer);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public Note addNote(String title, String body) {
        final Note n = new Note();
        n.id = getNewId();
        n.title = title;
        n.body = body;
        n.dateCreated = 0l;
        n.lastModified = n.dateCreated;
        notes.put(n.id, n);
        return n;
    }

    /**
     * getNewId returns a short string that is unique in notes keyset
     */
    private String getNewId() {
        final String alphaNum = "abcdefghijklmnopqrstuvwzyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * alphaNum.length());
            sb.append(alphaNum.charAt(index));
        }

        if(notes.containsKey(sb.toString()))
            return getNewId();

        return sb.toString();
    }

    @Override
    public Note updateNote(String noteId, String title, String body) {
        Note n = notes.get(noteId);
        n.title = title;
        n.body = body;
        return n;
    }

    @Override
    public boolean deleteNote(String noteId) {
        if(!notes.containsKey(noteId))
            return false;

        notes.remove(noteId);
        return true;
    }

    @Override
    public boolean archiveNote(String noteId) {
        if(!notes.containsKey(noteId)) return false;

        Note n = notes.get(noteId);
        n.isArchived = true;
        return true;
    }

    @Override
    public Collection<Note> getNotes() {
        // TODO: sort list by last_modified
        return notes.values();
    }
}
