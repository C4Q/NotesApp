package c4q.nyc.notesapp.models;

import android.content.Context;

import java.util.ArrayList;

/**
 * IDataSource describes the commons operations for managing a Notes data store.
 */

public abstract class IDataSource extends ArrayList<Note> {
    /**
     * Creates a new Note with the given title and body and returns it
     *
     * @param title
     * @param body
     * @return
     */
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

    /**
     * Updates the note with the given id
     *
     * @param noteId
     * @param title
     * @param body
     * @return
     */
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
     * Saves note data to non-volatile storage
     *
     * @param context
     * @param dataSource
     */
    public abstract void persist(Context context, IDataSource dataSource);

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
