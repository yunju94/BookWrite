package com.book.write.entity;

import com.book.write.constant.Role;
import com.book.write.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String nickname;
    private String tel;
    private String loginId;
    private String loginPassword;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createForm(MemberFormDto memberFormDto,  PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setNickname(memberFormDto.getNickname());
        member.setTel(memberFormDto.getTel());
        member.setLoginId(memberFormDto.getLoginId());
        String password = passwordEncoder.encode(memberFormDto.getLoginPassword());
        member.setLoginPassword(password);
        member.setEmail(memberFormDto.getEmail());
        member.setRole(Role.ADMIN);
        return member;
    }

}
