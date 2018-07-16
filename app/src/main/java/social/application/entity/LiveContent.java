package social.application.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chappy on 2018.06.22..
 */

public class LiveContent implements Serializable{

    public enum ContentType{
        VIDEO,
        IMAGE
    }

    private String id;

    private String title;

    private String contentURI;

    private List<String> tags;

    public ContentType contentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentURI() {
        return contentURI;
    }

    public void setContentURI(String contentURI) {
        this.contentURI = contentURI;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}
