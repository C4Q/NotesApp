package c4q.nyc.notesapp;

import java.util.List;

/**
 * Created by justiceo on 11/1/17.
 */

public interface INotesManager {
    boolean addNote(String title, String body);
    boolean updateNote(int noteId, String title, String body);
    boolean deleteNote(int noteId);
    boolean archiveNote(int noteId);
    List<Note> getNotes();
}
