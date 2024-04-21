package project.newmembership.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.newmembership.controller.form.MemberForm;

@Entity
@NoArgsConstructor
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private String name;
    private String tell;
    private String email;

    public Member(String username, String password, String nickname, String name, String tell, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.tell = tell;
        this.email = email;
    }

    public Member updateMember(MemberForm memberForm) {
        this.username = memberForm.getUsername();
        this.password = memberForm.getPassword();
        this.nickname = memberForm.getNickname();
        this.name = memberForm.getName();
        this.tell = memberForm.getTell();
        this.email = memberForm.getEmail();

        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
