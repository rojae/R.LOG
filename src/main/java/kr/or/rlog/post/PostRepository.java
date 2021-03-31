package kr.or.rlog.post;


import kr.or.rlog.common.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByOrderByCreatedDateDesc();
    public Page<Post> findAllByStatusOrderByCreatedDateDesc(Pageable pageable, Status status);
    public Page<Post> findAllByTitleContainsIgnoreCaseAndStatusOrderByCreatedDateDesc(Pageable pageable, String keyword, Status status);
}
