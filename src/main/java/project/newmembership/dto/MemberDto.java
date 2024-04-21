package project.newmembership.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {
    @NotEmpty(message = "회원 id입력은 필수입니다")
    private String username;
    @NotEmpty(message = "닉네임은 필수입니다")
    private String nickname;
    @NotEmpty(message = "이름 입력은 필수입니다")
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
