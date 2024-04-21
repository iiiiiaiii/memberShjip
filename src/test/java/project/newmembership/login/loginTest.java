package project.newmembership.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.newmembership.controller.form.MemberForm;
import project.newmembership.entity.Member;
import project.newmembership.repository.MemberRepository;
import project.newmembership.service.MemberService;

@SpringBootTest
@Transactional
public class loginTest {

    @Autowired
    MemberService memberService;

    @Test
    public void testJoin() {
        MemberForm memberForm = new MemberForm("qto1", "1234", "yk", "장영규", "01058337231", "qto1@naver.com");
        memberService.save(memberForm);
    }

    @Test
    public void testLogin() {
        MemberForm memberForm = new MemberForm("qto1", "1234", "yk", "장영규", "01058337231", "qto1@naver.com");
        memberService.save(memberForm);
        memberService.login("qto1", "1234");
    }

    @Test
    public void updateMember() {
        MemberForm memberForm = new MemberForm("qto1", "1234", "yk", "장영규", "01058337231", "qto1@naver.com");
        memberService.save(memberForm);
        Member qto1 = memberService.login("qto1", "1234");
        MemberForm newMemberForm = new MemberForm("qto2", "1235", "yy", "장영규", "01058337231", "qto1@naver.com");
        memberService.update(qto1,newMemberForm);
    }
}
