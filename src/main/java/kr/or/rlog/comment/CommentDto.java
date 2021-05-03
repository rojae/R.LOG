package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.common.Status;
import kr.or.rlog.post.Post;
import kr.or.rlog.utils.TimeUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private Long commentId;
    private String content;
    private AccountDto writer;
    private boolean mine;
    private Long parentId;
    private String modifiedDate;
    private List<CommentDto> subComments;
    private Status status;

    private Long postId;
    private String postTitle;

    public CommentDto(Long commentId, String content, AccountDto writer, LocalDateTime modifiedDate, Long parentId){
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = TimeUtils.dateTimeToYYYYMMDD(modifiedDate);
        this.parentId = parentId;
    }

    public CommentDto(Long commentId, String content, AccountDto writer, LocalDateTime modifiedDate, Long parentId, Status status){
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = TimeUtils.dateTimeToYYYYMMDD(modifiedDate);
        this.parentId = parentId;
        this.status = status;
    }

    public CommentDto(Long commentId, String content, AccountDto writer, Account user, LocalDateTime modifiedDate, Long parentId, Status status){
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = TimeUtils.dateTimeToYYYYMMDD(modifiedDate);
        this.parentId = parentId;
        this.setMine(checkMine(user));
        this.status = status;
    }

    public boolean checkMine(Account user){
        if(user == null)
            return false;
        else
            return (this.writer.getId().equals(user.getId()));
    }

    // Entity -> DTO
    public static CommentDto of(Comment comment) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Comment.class, CommentDto.class)
                .addMapping(Comment::getContent, CommentDto::setContent)
                .addMapping(Comment::getStatus, CommentDto::setStatus)
                .addMapping(Comment::getModifiedDate, CommentDto::setModifiedDate)
                .addMapping(Comment::getWriter, CommentDto::setWriter)
                .addMappings(mapper -> mapper.map(src -> src.getPost().getId(), CommentDto::setPostId))
                .addMappings(mapper -> mapper.map(src -> src.getPost().getTitle(), CommentDto::setPostTitle));

        return modelMapper.map(comment, CommentDto.class);
    }

}
