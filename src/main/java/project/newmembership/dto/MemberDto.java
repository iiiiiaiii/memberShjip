package project.newmembership.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {
    private String username;
    private String nickname;
    private String name;
    private String tell;
    private String email;

    public MemberDto(String username, String nickname, String name, String tell, String email) {
        this.username = username;
        this.nickname = nickname;
        this.name = name;
        this.tell = tell;
        this.email = email;
    }
}
