package bootmain.config.auth.dto;

import bootmain.web.domain.posts.User;
import lombok.Getter;

import javax.swing.*;
import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
