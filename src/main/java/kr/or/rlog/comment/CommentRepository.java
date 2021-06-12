package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.Status;
import kr.or.rlog.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositorySupport {
    List<Comment> findAllByStatus(Status enable);
    List<Comment> findAllByPost(Post postId);
    //Page<Comment> findAllByWriterAndStatusNot(Pageable pageable, Account writer, Status status);
}
