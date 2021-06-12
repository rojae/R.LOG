package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.Status;
import kr.or.rlog.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositorySupport {
    List<Comment> findAllByWriterOrStatusOrderByCreatedDateDesc(Account user, Status enable);
    List<Comment> findAllByPostAndStatusNotOrderByCreatedDateDesc(Post postId, Status status);
    Page<Comment> findAllByWriterAndStatusNot(Pageable pageable, Account writer, Status status);

}
