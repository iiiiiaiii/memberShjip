package project.newmembership.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.newmembership.controller.form.LoginForm;
import project.newmembership.controller.form.MemberForm;
import project.newmembership.dto.MemberDto;
import project.newmembership.entity.Member;
import project.newmembership.service.MemberService;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/user")
    public String newMember(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "newMember";
    }


    @PostMapping("/api/user/join")
    public ResponseEntity<String> joinMember(@Valid MemberForm form, BindingResult result) {
        System.out.println("dkaosdko = ");


        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
        Optional<Member> findId = memberService.findByUsername(form.getUsername());
        if (findId.isPresent()) {
            result.reject("IdError", "이미 존재하는 Id입니다");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Id already exists");
        }
        memberService.save(form);
        System.out.println("dkaosdko = ");
        return ResponseEntity.status(HttpStatus.CREATED).body("Member created successfully");
    }

    @GetMapping("/api/user/login")
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/api/user/loginTry")
    public String loginTry(@Valid LoginForm loginForm,BindingResult result) {
        Member findMember = memberService.login(loginForm.getUsername(), loginForm.getPassword());
        if (findMember == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            System.out.println("findMember = " + findMember);
            return "login";
        }
        System.out.println("findMember33 = " + findMember);
        return "redirect:/url/api/" + findMember.getUsername();
    }
    @GetMapping("/api/user/list")
    public String memberList(Model model, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<Member> all = memberService.findAll();
        List<MemberDto> allDto = memberService.findAllDto(pageable);
        System.out.println("allDto = " + allDto);
        return "memberList";
    }
}
