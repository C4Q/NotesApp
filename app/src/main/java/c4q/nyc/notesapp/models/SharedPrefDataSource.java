package c4q.nyc.notesapp.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implements IDataSource using HashMap as underlying storage
 */

public class SharedPrefDataSource extends ArrayList<Note> implements IDataSource {
    private static final String PREF_KEY = "notes_data";
    private static final String PREF_FILE = "notes_table";
    private static final String TAG = "SharedPrefDataSource";
    private static SharedPrefDataSource dataSource;
    private static SharedPreferences sharedPreferences;

    private SharedPrefDataSource() {
    }

    public static IDataSource getInstance(Context context) {
        if (dataSource != null) {
            return dataSource;
        }

        Type collectionType = new TypeToken<Collection<Note>>() {
        }.getType();
        Gson gs = new Gson();
        Collection<Note> notes = null;

        // try to load the data from shared Preferences
        sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        notes = gs.fromJson(new StringReader(sharedPreferences.getString(PREF_KEY, "")), collectionType);


        // above failed, use empty list
        if (notes == null) {
            Log.d(TAG, "No data was loaded from any storage, initializing empty notes manager");
            notes = new ArrayList<>();
        }

        dataSource = new SharedPrefDataSource();
        dataSource.addAll(notes);
        return dataSource;
    }

    /**
     * Serializes a notes to a json file
     *
     * @Param context
     */
    public void persist(Context context, IDataSource dataSource) {
        StringWriter writer = new StringWriter();
        new Gson().toJson(dataSource, writer);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY, writer.toString());
        editor.commit();
    }

    @Override
    public Note addNote(String title, String body) {
        final Note n = new Note();
        n.id = getNewId();
        n.title = title;
        n.body = body;
        n.dateCreated = 0l;
        n.lastModified = n.dateCreated;
        add(n);
        return n;
    }

    @Override
    public Note updateNote(String noteId, String title, String body) {
        for (Note n : this) {
            if (n.id.equals(noteId)) {
                n.title = title;
                n.body = body;
                n.lastModified++;
                return n;
            }
        }
        return null;
    }


    /**
     * getNewId returns a short string that is unique in notes keyset
     */
    private String getNewId() {
        final String alphaNum = "abcdefghijklmnopqrstuvwzyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * alphaNum.length());
            sb.append(alphaNum.charAt(index));
        }
        return sb.toString();
    }
}
