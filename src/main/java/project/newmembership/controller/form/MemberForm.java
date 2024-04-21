package project.newmembership.controller.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수입니다")
    @Size(min = 4, message = "최소 4글자 이상이어야 합니다")
    private String username;

    @NotEmpty(message = "비밀 번호 입력은 필수입니다")
    @Size(min = 4, message = "최소 4글자 이상이어야 합니다")
    private String password;

    @NotEmpty(message = "닉네임은 필수입니다")
    private String nickname;
    @NotEmpty(message = "이름 입력은 필수입니다")
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
