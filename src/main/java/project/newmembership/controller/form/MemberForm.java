package project.newmembership.controller.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberForm {

    private String username;
    private String password;
    private String nickname;
    private String name;
    private String tell;
    private String email;

    public MemberForm(String username, String password, String nickname, String name, String tell, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.tell = tell;
        this.email = email;
    }
}
