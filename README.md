the commerce 개발 과제

회원 entity구성 
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
    가입순서 정렬을 위한 createTime은 공통의 관심사이므로 BaseEntity에 저장

    
Repository구성
    @Query("select new project.newmembership.dto.MemberDto(m.username, m.nickname, m.name, m.tell, m.email)" +
            " from Member m" +
            " order by m.createDate, m.name ")
    List<MemberDto> findAllDto(Pageable pageable);
    @Query("select new project.newmembership.dto.MemberDto(m.username, m.nickname, m.name, m.tell, m.email)" +
            " from Member m" +
            " where m.username = :id")
    Optional<MemberDto> findDtoByUsername(String id);
    Optional<Member> findByUsername(String username);


WebConfig와 인터셉터를 통한 사용자관리
SecurityConfig를 통한 비밀번호 암호화 관리
    
