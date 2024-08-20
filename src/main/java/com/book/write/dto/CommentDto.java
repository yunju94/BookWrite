package com.book.write.dto;

import com.book.write.entity.Comment;
import com.book.write.entity.Member;
import com.book.write.entity.WriteDetail;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class CommentDto {
    private Long id;

    @Size(max = 600, message = "내용은 최대 600자까지 입력할 수 있습니다.")
    private String content;

    private Member member;//누가 쓴 건지 알아야하니까

    private WriteDetail writeDetail;//어디 댓글인지 알아야하니까

    //---------------------------------------------//

    private static ModelMapper modelMapper = new ModelMapper();

    public  static  CommentDto of(Comment comment){
        return  modelMapper.map(comment, CommentDto.class);
    }

    //------------------------------------------------------//

}
