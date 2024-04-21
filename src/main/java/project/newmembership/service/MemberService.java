package project.newmembership.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.newmembership.controller.form.MemberForm;
import project.newmembership.dto.MemberDto;
import project.newmembership.entity.Member;
import project.newmembership.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(MemberForm form) {
        Member member = new Member(form.getUsername(),
                form.getPassword(),
                form.getNickname(),
                form.getName(),
                form.getTell(),
                form.getEmail());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }
    @Transactional
    public void update(Member member, MemberForm memberForm) {
        Member updateMember = member.updateMember(memberForm);
        updateMember.setPassword(passwordEncoder.encode(updateMember.getPassword()));
        memberRepository.save(updateMember);
    }

    @Transactional
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    public Member login(String username, String password) {
        return memberRepository.findByUsername(username)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
    public List<MemberDto> findAllDto(Pageable pageable) {
        return memberRepository.findAllDto(pageable);
    }

    public Optional<Member> findByUsername(String id) {
        return memberRepository.findByUsername(id);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
