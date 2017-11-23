package c4q.nyc.notesapp.models;

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
    Note addNote(String title, String body);

    /**
     * Updates the title and body of Note whose id matches noteId
     * @param noteId
     * @param title
     * @param body
     * @return
     */
    Note updateNote(String noteId, String title, String body);

    /**
     * Returns true if successfully deletes the note with the given noteId
     * @param noteId
     * @return
     */
    boolean deleteNote(String noteId);

    /**
     * Returns true if successfully archived note with the given noteId
     * @param noteId
     * @return
     */
    boolean archiveNote(String noteId);

    /**
     * Returns all the Notes
     * @return
     */
    Collection<Note> getNotes();
}
