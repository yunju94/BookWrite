package com.book.write.entity;

import com.book.write.constant.Role;
import com.book.write.dto.MemberFormDto;
import com.book.write.dto.MemberPasswordDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

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



    public  Member (String name, String email){
        this.name = name;
        this.email = email;
    }

    public  Member(){}

    public static Member createForm(MemberFormDto memberFormDto,  PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setNickname(memberFormDto.getNickname());
        member.setTel(memberFormDto.getTel());
        member.setLoginId(memberFormDto.getLoginId());
        String password = passwordEncoder.encode(memberFormDto.getLoginPassword());
        member.setLoginPassword(password);
        member.setEmail(memberFormDto.getEmail());
        member.setRole(Role.USER);
        return member;
    }

    public  Member update(String name, String email, String picture){
        Member member = new Member();
        member.name = name;
        member.email = email;
        member.role=Role.USER;
        member.loginId =email;

        return member;
    }

    public  Member updateForm(MemberFormDto memberFormDto, Member member){
        member.setTel(memberFormDto.getTel());
        member.setLoginId(member.getEmail());
        member.setNickname(memberFormDto.getNickname());
        return member;
    }

    public  Member myPageUpdate(MemberFormDto memberFormDto, Member member){
        member.setName(memberFormDto.getName());
        member.setNickname(memberFormDto.getNickname());
        member.setTel(memberFormDto.getTel());

        return  member;
    }

    public static Member PasswordUpdate(Member member, MemberPasswordDto memberPasswordDto,
                                        PasswordEncoder passwordEncoder){
        String password = passwordEncoder.encode(memberPasswordDto.getLoginPassword());
        member.setLoginPassword(password);
        return member;
    }

}
