package com.book.write.service;

import com.book.write.dto.MemberFormDto;
import com.book.write.entity.Member;
import com.book.write.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private  final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public Member saveMemberForm(MemberFormDto memberFormDto){
        Member member = Member.createForm(memberFormDto,passwordEncoder);
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member;

    }
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByLoginId(member.getLoginId());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다."); // 예외 발생
        }

        Member findnickName = memberRepository.findByNickname(member.getNickname());
        if (findnickName != null) {
            throw new IllegalStateException("이미 가입된 별명입니다."); // 예외 발생
        }

    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId);

        if(member == null){
            throw new UsernameNotFoundException(loginId);
        }
        //빌더패턴
        return User.builder().username(member.getLoginId())
                .password(member.getLoginPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public  Member SearchIdtoName(String Id){
       Member member =  memberRepository.findByLoginId(Id);

       return member;
    }

    public  Member SearchNickName(String NickName){
        return memberRepository.findByNickname(NickName);
    }

    public  Member memberLoginId(String LoginId){
        return  memberRepository.findByLoginId(LoginId);
    }

    public  Member searchEmail(String email){
        return  memberRepository.findByEmail(email);
    }

    public  void  updateMemberForm(MemberFormDto memberFormDto, Member member){
        member.updateForm(memberFormDto, member, passwordEncoder);
    }
}
