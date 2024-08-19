package com.book.write.dto;

import com.book.write.entity.Member;
import com.book.write.entity.WriteInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MemberFormDto {

    private  Long id;

    private String name;


    private String nickname;


    private String tel;


    private String loginId;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 최소8자에서 최대 20자의 비밀번호여야 합니다.")
    private String loginPassword;

    @Email
    private String email;



    //---------------------------------------------//

    private static ModelMapper modelMapper = new ModelMapper();

    public  static  MemberFormDto of(Member member){
        return  modelMapper.map(member, MemberFormDto.class);
    }

    //------------------------------------------------------//




}
