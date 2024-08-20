package com.book.write.entity;

import com.book.write.dto.CommentDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @Size(max = 600, message = "내용은 최대 600자까지 입력할 수 있습니다.")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;//누가 쓴 건지 알아야하니까

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_detail_id")
    private WriteDetail writeDetail;//어디 댓글인지 알아야하니까

    //---------------------------------------------//

    private static ModelMapper modelMapper = new ModelMapper();

    public  static Comment of(CommentDto commentDto){
        return  modelMapper.map(commentDto, Comment.class);
    }

    //------------------------------------------------------//


    public  static Comment  createDto(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setWriteDetail(commentDto.getWriteDetail());
        comment.setMember(commentDto.getMember());
        comment.setContent(commentDto.getContent());
        return comment;
    }
}
