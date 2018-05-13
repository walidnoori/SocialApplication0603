package social.application.explore;

/**
 * Created by Chappy on 2018.04.01..
 */

public class ExploreObject {

    private long id;
    private String title;
    private int srcId;

    public ExploreObject(long id, String title, int srcId) {
        this.id = id;
        this.title = title;
        this.srcId = srcId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }
}
