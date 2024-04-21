package project.newmembership.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginForm {
    @NotEmpty(message = "id를 입력하세요")
    private String username;
    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password;
}
