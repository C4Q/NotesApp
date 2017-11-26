package c4q.nyc.notesapp.models;

import android.content.Context;

import java.util.List;

/**
 * IDataSource describes the commons operations for managing a Notes data store.
 */

public interface IDataSource extends List<Note> {
    /**
     * Creates a new Note with the given title and body and returns it
     *
     * @param title
     * @param body
     * @return
     */
    Note addNote(String title, String body);

    /**
     * Updates the note with the given id
     *
     * @param noteId
     * @param title
     * @param body
     * @return
     */
    Note updateNote(String noteId, String title, String body);

    /**
     * Saves note data to non-volatile storage
     *
     * @param context
     * @param dataSource
     */
    void persist(Context context, IDataSource dataSource);

}
