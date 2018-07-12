package social.application.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chappy on 2018.07.05..
 */

public class User  implements Serializable{

    private Long id;

    private String userName;

    private List<LivePicture> livePictures;

    private List<LocalPicture> localPictures;

    private List<Event> events;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<LivePicture> getLivePictures() {
        return livePictures;
    }

    public void setLivePictures(List<LivePicture> livePictures) {
        this.livePictures = livePictures;
    }

    public List<LocalPicture> getLocalPictures() {
        return localPictures;
    }

    public void setLocalPictures(List<LocalPicture> localPictures) {
        this.localPictures = localPictures;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
