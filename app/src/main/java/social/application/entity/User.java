package social.application.entity;

import java.io.Serializable;

/**
 * Created by Chappy on 2018.07.05..
 */

public class User  implements Serializable{

    private Long id;

    private String userName;

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
}
