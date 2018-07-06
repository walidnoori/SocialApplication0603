package social.application.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chappy on 2018.06.22..
 */

public class LivePicture implements Serializable{

    private String id;

    private String title;

    private String imageURI;

    private List<String> tags;

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

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
