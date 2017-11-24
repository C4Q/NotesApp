package c4q.nyc.notesapp.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;

/**
 * INotesManager describes the commons operations for managing a Notes data store.
 */

public interface INotesManager {
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
}
