package c4q.nyc.notesapp.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;

import c4q.nyc.notesapp.NotesListActivity;

/**
 * IDataSource describes the commons operations for managing a Notes data store.
 */

public interface IDataSource {
    /**
     * Creates a new Note with the given title and body and returns it
     * @param title
     * @param body
     * @return
     */
    Note createNote(String title, String body);


    /**
     * Saves note data to non-volatile storage
     * @param context
     * @param notesList
     */
    void persist(Context context, ArrayList<Note> notesList);

    /**
     * Loads data from some storage into an arrayList
     * @param context
     * @return
     */
    ArrayList<Note> getData(Context context) throws Exception;
}
