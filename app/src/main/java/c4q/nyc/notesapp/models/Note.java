package c4q.nyc.notesapp.models;

import java.io.Serializable;

/**
 * Created by justiceo on 11/1/17.
 */

public class Note implements Serializable {
    public String id;
    public boolean isArchived;
    public String title;
    public String body;
    public long dateCreated;
    public long lastModified;
}
