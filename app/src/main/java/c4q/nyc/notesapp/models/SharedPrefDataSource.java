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

public class SharedPrefDataSource extends IDataSource {
    private static final String PREF_KEY = "notes_data";
    private static final String PREF_FILE = "notes_file";
    private static final String TAG = "SharedPrefDataSource";
    private static SharedPrefDataSource dataSource;
    private static SharedPreferences sharedPreferences;

    // prevent having multiple instances of this class
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

        // read json-formatted notes data into a collection.
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

    public void persist(Context context, IDataSource dataSource) {
        // serialize our notes data to a json string
        StringWriter writer = new StringWriter();
        new Gson().toJson(dataSource, writer);

        // save it in shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY, writer.toString());
        editor.commit();
    }
}
