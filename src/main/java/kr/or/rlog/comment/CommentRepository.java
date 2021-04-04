package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.Status;
import kr.or.rlog.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByCreatedDateDesc(Post post);
    List<Comment> findAllByStatus(Status enable);
    List<Comment> findAllByWriterOrStatusOrderByCreatedDateDesc(Account user, Status enable);
    List<Comment> findAllByPostAndStatusNotOrderByCreatedDateDesc(Post postId, Status status);
}
