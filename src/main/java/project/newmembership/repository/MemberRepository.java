package project.newmembership.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.newmembership.dto.MemberDto;
import project.newmembership.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    @Query("select new project.newmembership.dto.MemberDto(m.username, m.nickname, m.name, m.tell, m.email)" +
            " from Member m" +
            " order by m.createDate, m.name ")
    List<MemberDto> findAllDto(Pageable pageable);

    @Query("select new project.newmembership.dto.MemberDto(m.username, m.nickname, m.name, m.tell, m.email)" +
            " from Member m" +
            " where m.username = :id")
    Optional<MemberDto> findDtoByUsername(String id);
    Optional<Member> findByUsername(String username);
}
