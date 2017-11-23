package c4q.nyc.notesapp;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by justiceo on 11/1/17.
 */

@XmlRootElement
public class Note implements Serializable{
    @XmlAttribute
    public String id;
    @XmlAttribute
    public boolean isArchived;
    @XmlElement
    public String title;
    @XmlElement
    public String body;
    @XmlElement
    public long dateCreated;
    @XmlElement
    public long lastModified;
}
