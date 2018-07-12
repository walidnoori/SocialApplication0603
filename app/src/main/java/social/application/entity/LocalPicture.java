package social.application.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chappy on 2018.06.22..
 */

public class LocalPicture implements Serializable {

    private String id;

    private String name;

    private String imageURI;

    public LocalPicture() {
    }

    public LocalPicture(String id, String name, String imageURI) {
        this.id = id;
        this.name = name;
        this.imageURI = imageURI;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}

