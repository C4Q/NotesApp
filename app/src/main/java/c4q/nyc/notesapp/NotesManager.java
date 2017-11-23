package c4q.nyc.notesapp;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashMap;

/**
 * Implements INotesManager using HashMap as underlying storage
 */

public class NotesManager implements INotesManager {
    private HashMap<String, Note> notes = new HashMap<>();

    /**
     * Deserializes a Notes xml file into an INotesManager
     * @param filePath
     * @return
     */
    static INotesManager FromFile(String filePath) throws Exception {
        // Read it back
        JAXBContext readCtx = JAXBContext.newInstance(Note.class);
        Unmarshaller unmarshaller = readCtx.createUnmarshaller();

        Collection<Note> notesCollection = unmarshaller.unmarshal(new FileReader(filePath));

        NotesManager notesManager = new NotesManager();
        for(Note n: notesCollection) {
            notesManager.notes.put(n.id, n);
        }
        return notesManager;
    }

    /**
     * Serializes a notes to an xml file and returns true if successful
     * @param notesManager
     * @return boolean
     */
    static boolean ToFile(INotesManager notesManager, String filePath) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(Note.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // write it to a stream
        FileWriter writer = new FileWriter(filePath);
        marshaller.marshal(notesManager.getNotes(), writer);
        writer.close();
        return true;
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
        return notes.values();
    }
}
