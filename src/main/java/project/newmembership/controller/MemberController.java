package project.newmembership.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.newmembership.controller.form.LoginForm;
import project.newmembership.controller.form.MemberForm;
import project.newmembership.dto.MemberDto;
import project.newmembership.entity.Member;
import project.newmembership.service.MemberService;
import org.springframework.ui.Model;
import project.newmembership.session.SessionConst;

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
    public String loginPage(@ModelAttribute("loginForm") LoginForm form) {
        return "login";
    }

    @PostMapping("/api/user/loginTry")
    public String loginTry(@Valid LoginForm loginForm, BindingResult result,
                           HttpServletRequest request) {
        Member findMember = memberService.login(loginForm.getUsername(), loginForm.getPassword());
        if (findMember == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }
        System.out.println("findMember = " + findMember);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, findMember);
        session.setAttribute("username",findMember.getUsername());
        String username = findMember.getUsername();
        return "redirect:/" + username;
    }
    @GetMapping("/api/user/{username}")
    public String updateMember(@PathVariable("username")String username,
                               Model model) {
        Optional<Member> byUsername = memberService.findByUsername(username);
        Member member = byUsername.orElse(null);
        // MemberForm 객체 생성 및 멤버 정보 설정
        MemberForm memberForm = new MemberForm();
        memberForm.setUsername(member.getUsername());
        memberForm.setNickname(member.getNickname());
        memberForm.setName(member.getName());
        memberForm.setTell(member.getTell());
        memberForm.setEmail(member.getEmail());
        // 모델에 추가
        model.addAttribute("memberForm", memberForm);
        return "update";
    }

    @PostMapping("/api/user/{username}")
    public String updateMember2(@PathVariable("username") String username,
                                @Valid @ModelAttribute MemberForm form,
                                HttpSession session) {
        Optional<Member> byUsername = memberService.findByUsername(username);
        Member member = byUsername.orElse(null);
        memberService.update(member,form);
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/api/user/list")
    public String memberList(Model model, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<MemberDto> allDto = memberService.findAllDto(pageable);
        model.addAttribute("allDto", allDto);
        return "memberList";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("request = " + request);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        //캐시제거
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        return "redirect:/";
    }

    private static String expireCookie(HttpServletResponse response,String CookieId) {
        Cookie cookie = new Cookie(CookieId, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
